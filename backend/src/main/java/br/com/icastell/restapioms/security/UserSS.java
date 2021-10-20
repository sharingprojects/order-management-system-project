package br.com.icastell.restapioms.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.icastell.restapioms.domain.customer.enums.ProfileType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Getter
	private Integer id;
	private String email;
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserSS(Integer id, String email, String password, Set<ProfileType> profiles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = profiles.stream().map(p -> new SimpleGrantedAuthority(p.getDescription()))
				.collect(Collectors.toList());
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// default NonExperid
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// default NonLocked
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// default NonExpired
		return true;
	}

	@Override
	public boolean isEnabled() {
		// defaut enabled
		return true;
	}

	public boolean hasRole(ProfileType profile) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
	}

}
