import com.cslab.blovi.configure.mapper.UserMapper
import com.cslab.blovi.configure.provider.JwtTokenProvider
import com.cslab.blovi.dto.RequestDTO
import com.cslab.blovi.dto.ResponseDTO
import com.cslab.blovi.dto.UserDTO
import com.cslab.blovi.service.UserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserDetailsServiceImpl (
    val userMapper: UserMapper,
    val passwordEncoder: PasswordEncoder,
    val jwtTokenProvider: JwtTokenProvider
)  {

    fun login(requestDTO: RequestDTO ) : ResponseEntity<ResponseDTO>{
        // 에러메세지 만들어야됨
        val findMember:UserDTO = findByMember(requestDTO.getEmail())
        validateMachedPassword(requestDTO.getPassword(), findMember.getPassword())
        var accessToken:String = jwtTokenProvider.createAccessToken(findMember.nickname,findMember.role)
        val responseDTO:ResponseDTO = ResponseDTO(findMember,accessToken)
        return ResponseEntity.ok(responseDTO)
    }

    fun validateMachedPassword(vaildpassword: String , userpassword : String ) {
        if (!passwordEncoder.matches(vaildpassword,userpassword)) {
            throw IndexOutOfBoundsException("수정해야됨")
        }
        // Validation logic for the user
    }

     fun findByPassword(password :String) : UserDTO{
         return userMapper.findUserByPassword(password)
             ?: throw UsernameNotFoundException("해당 사용자 없다 새끼야 가라 :${password}")
     }

    fun findByMember(email :String) : UserDTO{
        return userMapper.findUserByEmail(email)
            ?: throw UsernameNotFoundException("해당 사용자 없다고 : ${email}")
    }

}

