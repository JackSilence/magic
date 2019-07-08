package magic.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;

@Service
public class Cloudinary {
	public String upload( Object file, String publicId ) {
		try {
			return url( uploader().upload( file, ObjectUtils.asMap( "public_id", publicId ) ) );

		} catch ( IOException e ) {
			throw new RuntimeException( e );

		}
	}

	public String explicit( String publicId ) {
		try {
			return url( uploader().explicit( publicId, ObjectUtils.asMap( "type", "upload" ) ) );

		} catch ( IOException | RuntimeException e ) {
			return StringUtils.EMPTY;

		}
	}

	private Uploader uploader() {
		return new com.cloudinary.Cloudinary().uploader();
	}

	private String url( Map<?, ?> map ) {
		return map.get( "secure_url" ).toString();
	}
}