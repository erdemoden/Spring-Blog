package com.project.blog.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.blog.DTOS.FollowedBlogs;
import com.project.blog.DTOS.PostLikeId;
import com.project.blog.entities.Blogs;
import com.project.blog.entities.Likes;
import com.project.blog.entities.Posts;
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
		List<FollowedBlogs> followedBlogs = new LinkedList<>(user.get().getFollowerBlogs().stream().map(followedblog->modelMapper.map(followedblog,FollowedBlogs.class)).collect(Collectors.toList()));
		followedBlogs.addAll(user.get().getAdminBlogs().stream().map(adminblog->modelMapper.map(adminblog,FollowedBlogs.class)).collect(Collectors.toList()));
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
	public PictureResponse getUsersPhoto(String username){
		PictureResponse pictureResponse = new PictureResponse();
		if(findByUserName(username) == null){
			pictureResponse.setError("There is no such an user");
			return pictureResponse;
		}
		try {
			Map result  = cloudinary.search().expression("filename:"+username).execute();
			List<Map> resources = (List<Map>) result.get("resources");
			if (resources.size() > 0) {
				Map resource = resources.get(0);
				String url = (String) resource.get("url");
				pictureResponse.setPicPath(url);
				return pictureResponse;
			}
			else{
				pictureResponse.setPicPath(userlogo);
				return pictureResponse;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	//TODO BURAYI BÜYÜK İHTİMAL SİLECEĞİM
	public void checkMail(String mail) {
		
	}

	private String extractJwtFromString(String bearer) {
		if(StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}
	// TODO : userın postlarını getir
	public List<PostLikeId> getUserPosts(String Authorization){
		Optional<User> user = getUserFromAuth(Authorization);
		List<Posts> posts = user.get().getPosts();
		List<PostLikeId> postLikeIds = new ArrayList<>();
		posts.stream().forEach(post->{
			PostLikeId postLikeId = new PostLikeId();
			postLikeId.setPost(post.getPost());
			postLikeId.setUserName(user.get().getUsername());
			postLikeId.setUserPhoto(post.getUser().getUserphoto());
			postLikeId.setId(post.getId());
			postLikeId.setComments(post.getComments());
			postLikeId.setLikes(post.getLikes().size());
			postLikeIds.add(postLikeId);
		});
		return postLikeIds;
	}
	// TODO : Userın beğendiği postları getir
	public List<PostLikeId> getLikedPosts(String Authorization){
		Optional<User> user = getUserFromAuth(Authorization);
		List<Likes> likes = user.get().getLikes();
		List <Posts> posts = likes.stream().map(like->like.getPosts()).collect(Collectors.toList());
		List<PostLikeId> postLikeIds = new ArrayList<>();
		posts.stream().forEach(post->{
			PostLikeId postLikeId = new PostLikeId();
			postLikeId.setPost(post.getPost());
			postLikeId.setUserName(post.getUser().getUsername());
			postLikeId.setUserPhoto(post.getUser().getUserphoto());
			postLikeId.setId(post.getId());
			postLikeId.setComments(post.getComments());
			postLikeId.setLikes(post.getLikes().size());
			postLikeIds.add(postLikeId);
		});
		return postLikeIds;
	}
	public OwnerFollower checkAdminAndOwner(String adminName,String Authorization){
		OwnerFollower ownerFollower = new OwnerFollower();
		User owner = getUserFromAuth(Authorization).orElse(null);
		User admin = userRepository.findByUsername(adminName);
		if(owner==null||admin==null){
			ownerFollower.setError("Something Went Wrong Please Refresh The Page");
			return ownerFollower;
		}
		if(owner.getOwnerBlogs().size()>0){
			ownerFollower.setOwner(true);
			ownerFollower.setOwnerBlogs(owner.getOwnerBlogs().stream().map(ownerblog->modelMapper.map(ownerblog,FollowedBlogs.class)).collect(Collectors.toList()));
		}
		if(admin.getAdminBlogs().size()>0){
			ownerFollower.setAdmin(true);
			ownerFollower.setAdminBlogs(admin.getAdminBlogs().stream().map(adminblog->modelMapper.map(adminblog,FollowedBlogs.class)).collect(Collectors.toList()));
		}
		return ownerFollower;
	}

	public void thirtyMinuteBlock(String Authorization){
		User user = getUserFromAuth(Authorization).orElse(null);
		if(user!=null) {
			user.setBlocked(LocalDateTime.now());
			userRepository.save(user);
		}
	}

	public void deleteTimeBlock(String Authorization){
		User user = getUserFromAuth(Authorization).orElse(null);
		if(user!=null) {
			user.setBlocked(null);
			userRepository.save(user);
		}
	}

	public boolean isUserBlockExist(String Authorization){
		User user = getUserFromAuth(Authorization).orElse(null);
		if(user!=null && user.getBlocked()!=null){
			return true;
		}
		return false;
	}
	public boolean checkUserBlock(User user){
		LocalDateTime now = LocalDateTime.now();
		if(user == null || user.getBlocked()==null){
			return false;
		}
		Duration duration = Duration.between(user.getBlocked(),now);
		if(duration.toHours()<24 && duration.toMinutes()>=1){
			System.out.println(duration.toHours());
			System.out.println(duration.toMinutes());
			return true;
		}
		System.out.println(duration.toHours());
		System.out.println(duration.toMinutes());
		return false;
	}
	public void ifBlockExpiredUpdate(User user){
		if(user.getBlocked()!=null){
			Duration duration = Duration.between(user.getBlocked(),LocalDateTime.now());
			if(duration.toHours()>=24){
				//System.out.println(duration.toHours());
				user.setBlocked(LocalDateTime.now());
				userRepository.save(user);
			}
		}
	}

}
