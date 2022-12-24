package com.jeppu.services;

import com.jeppu.entities.Category;
import com.jeppu.entities.Post;
import com.jeppu.entities.User;
import com.jeppu.exceptions.ResourceNotFoundException;
import com.jeppu.payloads.CategoryDTO;
import com.jeppu.payloads.PageResponse;
import com.jeppu.payloads.PostDTO;
import com.jeppu.payloads.UserDTO;
import com.jeppu.repositories.CategoryRepo;
import com.jeppu.repositories.PostRepo;
import com.jeppu.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        //retrieve User and Category from User/Category tables using repository via their respective Ids
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        //convert postDTO to Post entity to save in DB
        Post post = this.modelMapper.map(postDTO, Post.class);
        //set default values attributes
        post.setImageName("default.png");
        post.setAddedDate(LocalDate.now());
        //save the user and category fields in post object
        post.setUser(user);
        post.setCategory(category);
        //save post in db
        Post savedPost = this.postRepo.save(post);
        PostDTO savedPostDTO = this.modelMapper.map(savedPost, PostDTO.class);
        savedPostDTO.setUserDTO(this.modelMapper.map(user, UserDTO.class));
        savedPostDTO.setCategoryDTO(this.modelMapper.map(category, CategoryDTO.class));
        return savedPostDTO;
        //return this.modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long postId) {
        Post postFromDB = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Category category = this.categoryRepo.findById(postFromDB.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", postFromDB.getCategory().getId()));
        postFromDB.setTitle(postDTO.getTitle());
        postFromDB.setContent(postDTO.getContent());
        postFromDB.setImageName(postDTO.getImageName());
        postFromDB.setCategory(category);
        //Category category = this.modelMapper.map(postDTO.getCategoryDTO(), Category.class);
        //User user = this.modelMapper.map(postDTO.getUserDTO(), User.class);
        //postFromDB.setCategory(category);
        //postFromDB.setUser(user);
        Post savedPost = this.postRepo.save(postFromDB);
        return convertPostToPostDTO(savedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post postFromDB = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        this.postRepo.delete(postFromDB);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        return convertPostToPostDTO(post);
    }

    @Override
    public PageResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(pageable);
        List<Post> allPosts = pagePost.getContent();
        List<PostDTO> allPostDTOs = convertPostListToPostDTOList(allPosts);
        PageResponse pageResponse = PageResponse.builder()
                .allPosts(allPostDTOs)
                .pageNumber(pagePost.getNumber())
                .pageSize(pagePost.getSize())
                .totalElements(pagePost.getTotalElements())
                .totalPages(pagePost.getTotalPages())
                .isLastPage(pagePost.isLast())
                .build();
        return pageResponse;
    }


    @Override
    public PageResponse getAllPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        Page<Post> pagePost = this.postRepo.findByCategory(category, pageable);
        List<Post> allPosts = pagePost.getContent();
        List<PostDTO> postDTOS = convertPostListToPostDTOList(allPosts);
        PageResponse pageResponse = PageResponse.builder()
                .allPosts(postDTOS)
                .pageNumber(pagePost.getNumber())
                .pageSize(pagePost.getSize())
                .totalElements(pagePost.getTotalElements())
                .totalPages(pagePost.getTotalPages())
                .isLastPage(pagePost.isLast())
                .build();
        return pageResponse;
    }

    @Override
    public PageResponse getAllPostsByUser(Long userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Page<Post> pagePost = this.postRepo.findByUser(user, pageable);
        List<Post> allPostsList = pagePost.getContent();
        List<PostDTO> allPostDTOs = convertPostListToPostDTOList(allPostsList);
        PageResponse pageResponse = PageResponse.builder()
                .allPosts(allPostDTOs)
                .pageNumber(pagePost.getNumber())
                .pageSize(pagePost.getSize())
                .totalElements(pagePost.getTotalElements())
                .totalPages(pagePost.getTotalPages())
                .isLastPage(pagePost.isLast())
                .build();
        return pageResponse;
    }

    private PostDTO convertPostToPostDTO(Post post) {
        UserDTO postUserDTO = this.modelMapper.map(post.getUser(), UserDTO.class);
        CategoryDTO postCategoryDTO = this.modelMapper.map(post.getCategory(), CategoryDTO.class);
        PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
        postDTO.setUserDTO(postUserDTO);
        postDTO.setCategoryDTO(postCategoryDTO);
        return postDTO;
    }

    private List<PostDTO> convertPostListToPostDTOList(List<Post> allPosts) {
        return allPosts
                .stream()
                .map(
                        post -> {
                            return convertPostToPostDTO(post);
                        })
                .collect(Collectors.toList());
    }




}
