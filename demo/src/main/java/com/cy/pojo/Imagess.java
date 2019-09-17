package com.cy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Imagess {
	private Integer error=0;//表示用户上传文件是否有错
	private String url;//图片的虚拟路径
	private Integer width;//宽度
	private Integer height;//高度
}
