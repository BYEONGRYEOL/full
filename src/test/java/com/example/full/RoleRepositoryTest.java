package com.example.full;

import com.example.full.entity.member.Role;
import com.example.full.entity.member.RoleType;
import com.example.full.exception.RoleNotFoundException;
import com.example.full.repository.member.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;

    @PersistenceContext
    EntityManager em;

    private void clear(){
        em.flush();
        em.clear();
    }

    @Test
    void createAndReadTest() {
        //given
        Role role = createRole();
        //when
        roleRepository.save(role);
        clear();
        //then

        Role foundRole = roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new);
        assertThat(role.getId()).isEqualTo(foundRole.getId());
    }

    @Test
    void deleteTest() {
        //given
        Role role = roleRepository.save(createRole()); // save 함수가 저장한 entity클래스를 리턴하니까 이렇게도 사용 가능
        clear();
        //when
        roleRepository.delete(role);
        //then
        assertThatThrownBy(()->roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new))
                .isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void uniqueRoleTypeTest() {
        //given
        roleRepository.save(createRole());
        clear();
        //when
        //then
        assertThatThrownBy(()->roleRepository.save(createRole()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
    Role createRole(){
        return new Role(RoleType.ROLE_NORMAL);
    }
}
