package com.board.domain;

import com.board.support.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FileInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_info_id")
    private Long id;

    @NotEmpty
    @Column
    private String fileName;

    @NotEmpty
    @Column
    private String fileOriginName;

    @NotEmpty
    @Column
    private String fileUrl;


}
