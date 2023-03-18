package com.project.blog.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "blogs")
@Data
public class Blogs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,name = "title")
    private String title;

    private String subject;

    @ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="ownerid")
    @JsonIgnore
    private User owner;
    @ManyToMany(fetch = FetchType.LAZY,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "adminblog",joinColumns = {
            @JoinColumn(name = "blogid",referencedColumnName = "id")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "adminid",referencedColumnName = "id")
            }
    )
    @JsonIgnore
    private List<User> admins;
    @ManyToMany(fetch = FetchType.LAZY,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "followerblog",joinColumns = {
            @JoinColumn(name = "blogid",referencedColumnName = "id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "followerid",referencedColumnName = "id")
            }
    )
    @JsonIgnore
    private List<User> followers;
    @OneToMany(mappedBy = "blogs",cascade = {CascadeType.ALL})
    private List<Posts> posts;

}
