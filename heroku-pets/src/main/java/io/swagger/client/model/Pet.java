package io.swagger.client.model;



import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class Pet  {
  
  @SerializedName("name")
  private String name = null;
  @SerializedName("birthday")
  private Integer birthday = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getBirthday() {
    return birthday;
  }
  public void setBirthday(Integer birthday) {
    this.birthday = birthday;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Pet {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  birthday: ").append(birthday).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}


