package com.example.chaletkoreabackend.service;

import com.example.chaletkoreabackend.dto.comment.CommentDto;
import com.example.chaletkoreabackend.entity.comment.Comment;
import com.example.chaletkoreabackend.repository.CommentRepository;
import com.example.chaletkoreabackend.repository.CooperationRepository;
import com.example.chaletkoreabackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CooperationRepository cooperationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    //댓글생성

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .cooperation(cooperationRepository.findById(commentDto.getCooperationId()).orElse(null))
                .employee(employeeRepository.findById(commentDto.getEmployeeId()).orElse(null))
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Comment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }
    //댓글 보기
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(this::convertToDto).orElse(null);
    }

    //댓글수정

    public CommentDto updateComment(Long id, CommentDto commentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            Comment updatedComment = commentRepository.save(comment);
            return convertToDto(updatedComment);
        }
        return null;
    }


    //댓글삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }



    private CommentDto convertToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .cooperationId(comment.getCooperation().getCooperationId())
                .employeeId(comment.getEmployee().getEmployeeId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

}
