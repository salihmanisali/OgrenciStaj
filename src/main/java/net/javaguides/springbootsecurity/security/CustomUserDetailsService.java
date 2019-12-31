package net.javaguides.springbootsecurity.security;

import net.javaguides.springbootsecurity.entities.Role;
import net.javaguides.springbootsecurity.entities.User;
import net.javaguides.springbootsecurity.repositories.RoleRepository;
import net.javaguides.springbootsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Salih Efe
 *
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getAdi()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

	private Role ogrenciRole;

	private Role ogretmenRole;

	private Role firmaRole;

	private Role adminRole;


	public Role getOgrenciRole() {
		if(ogrenciRole==null) ogrenciRole=roleRepository.findById(5).get();
		return ogrenciRole;
	}

	public Role getOgretmenRole() {
		if(ogretmenRole==null) ogretmenRole=roleRepository.findById(4).get();
		return ogretmenRole;
	}

	public Role getFirmaRole() {
		if(firmaRole==null) firmaRole=roleRepository.findById(3).get();
		return firmaRole;
	}

	public Role getAdminRole() {
		if(adminRole==null) adminRole=roleRepository.findById(1).get();
		return adminRole;
	}
}