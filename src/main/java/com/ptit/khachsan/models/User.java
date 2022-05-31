package com.ptit.khachsan.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.
                                          SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@Entity
@Table(name = "user")

public class User  implements UserDetails{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int userId;
	@NotNull
	@Size(min=3, message="Name must be at least 3 characters long")
    private String username,password;
	@Column(name="full_name")
	private String fullName;
	@Column(name="phone_number")
	private String phoneNumber;
	private String address;
    private String role;
   public User() {
	   
   }
  
  
	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return Arrays.asList(
	    		new SimpleGrantedAuthority(role)
	    		);
	  }

	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }


	public User(@NotNull @Size(min = 3, message = "Name must be at least 3 characters long") String username,
			@NotNull @Size(min = 3, message = "Name must be at least 3 characters long") String password,
			String fullName, String role) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
	}
	

	
    
}
