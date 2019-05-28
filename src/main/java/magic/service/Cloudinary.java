package magic.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.cloudinary.utils.ObjectUtils;

@Service
public class Cloudinary {
	public String upload( Object file, String publicId ) {
		try {
			return new com.cloudinary.Cloudinary().uploader().upload( file, ObjectUtils.asMap( "public_id", publicId ) ).get( "secure_url" ).toString();

		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}
}