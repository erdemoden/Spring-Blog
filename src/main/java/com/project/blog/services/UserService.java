package com.project.blog.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.blog.DTOS.FollowedBlogs;
import com.project.blog.entities.Blogs;
import com.project.blog.responses.OwnerFollower;
import com.project.blog.responses.PictureResponse;
import com.project.blog.security.JwtTokenProvider;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.repositories.UserRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class UserService {

	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	private Cloudinary cloudinary;
	private ModelMapper modelMapper;
	@Value("${blog.app.userlogo}")
	private String userlogo;
	@Autowired
	public UserService(UserRepository userRepository, JwtTokenProvider tokenProvider,Cloudinary cloudinary,ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.jwtTokenProvider = tokenProvider;
		this.cloudinary = cloudinary;
		this.modelMapper = modelMapper;
	}
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void save(User user) {
			userRepository.save(user);
	} 
	
	public User findById(long id) {
		
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void deleteUserById(long id) {
		try {
		userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("merhaba");
		}
	}
	public OwnerFollower isOwner(String username,String title){
		User user = findByUserName(username);
		OwnerFollower ownerFollower = new OwnerFollower();
		if(user!=null){
			if(user.getOwnerBlogs().stream().filter(owner->owner.getTitle().equals(title)).findFirst().isPresent()){
				ownerFollower.setOwner(true);
			}
			if(user.getFollowerBlogs().stream().filter(owner->owner.getTitle().equals(title)).findFirst().isPresent()){
				ownerFollower.setFollower(true);
			}
			if(user.getAdminBlogs().stream().filter(owner->owner.getTitle().equals(title)).findFirst().isPresent()){
				ownerFollower.setAdmin(true);
			}
		}
		return ownerFollower;
	}
	public PictureResponse saveUserPic(MultipartFile userpic, String Authorization){
		//String path = System.getProperty("user.dir") + "/";
		PictureResponse pictureResponse = new PictureResponse();
		Optional<User> user = getUserFromAuth(Authorization);
		//String check = checkPicture(Authorization);

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String extension = userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/") + 1);
			Thumbnails.of(userpic.getInputStream())
					.size(300, 300) // Change the size to your desired dimensions
					.outputFormat(extension)
					.outputQuality(0.8) // Change the output quality (0.0 to 1.0)
					.toOutputStream(outputStream);

			ByteArrayInputStream compressedInputStream = new ByteArrayInputStream(outputStream.toByteArray());
			Map<String, String> uploadResult = cloudinary.uploader().upload(compressedInputStream.readAllBytes(), ObjectUtils.asMap(
					"folder", "user_pics/",
					"public_id", user.get().getUsername(),
					"overwrite", true
			));
			String uploadedImageUrl = uploadResult.get("url");
			userRepository.updatePhoto(uploadedImageUrl, user.get().getId());
			pictureResponse.setPicPath(uploadedImageUrl);
			return pictureResponse;
		}
		catch(IOException e){
			pictureResponse.setError("Something Went Wrong");
			return pictureResponse;
		}
		/*if(check.equals("error")) {
			String fileName = user.get().getUsername();
			String extension = userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/") + 1);
			Path saveTo = Paths.get(path, fileName + "." + extension);
			try {
				Files.copy(userpic.getInputStream(), saveTo);
				userRepository.updatePhoto(String.valueOf(saveTo),user.get().getId());
				pictureResponse.setPicPath(saveTo.toString());
				return pictureResponse;
			} catch (IOException e) {
				pictureResponse.setError("Something Went Wrong");
				return pictureResponse;
			}
		}
		else{
			File myFile =  new File(check);
			myFile.delete();
			String fileName = user.get().getUsername();
			String extension = userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/") + 1);
			Path saveTo = Paths.get(path, fileName + "." + extension);
			try {
				Files.copy(userpic.getInputStream(), saveTo);
				userRepository.updatePhoto(String.valueOf(saveTo),user.get().getId());
				pictureResponse.setPicPath(saveTo.toString());
				return pictureResponse;
			} catch (IOException e) {
				System.out.println("not an error");
			}
			return pictureResponse;
		}*/
	}
	@Transactional
	public List<FollowedBlogs> getFollowedBlogs(String Authorization){
		Optional<User> user = getUserFromAuth(Authorization);
		List<FollowedBlogs> followedBlogs = user.get().getFollowerBlogs().stream().map(followedblog->modelMapper.map(followedblog,FollowedBlogs.class)).collect(Collectors.toList());
		return followedBlogs;
	}
	public String getFile(String location){
		if(cloudinary.url().publicId(location).generate().equals("http://res.cloudinary.com/dbxchbci8/image/upload/")){
			System.out.println(userlogo);
			return userlogo;
		}
		return cloudinary.url().publicId(location).generate();
		/*try {
			return new FileSystemResource(Paths.get(location));
		} catch (Exception e) {
			// Handle access or file not found problems.
			throw new RuntimeException();
		}*/
	}
	public Optional<User> getUserFromAuth(String Authorization){
		String bearer = extractJwtFromString(Authorization);
		long id = jwtTokenProvider.getUserIdFromJwt(bearer);
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	public String checkPicture(String Authorization){
		Optional<User> user = getUserFromAuth(Authorization);
		if(user.get().getUserphoto()!=null){
			return user.get().getUserphoto();
		}
		else{
			return "error";
		}
	}
	public void checkMail(String mail) {
		
	}

	private String extractJwtFromString(String bearer) {
		if(StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}

}
