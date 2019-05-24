package magic.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudinary.utils.ObjectUtils;

public class Cloudinary {
	@Autowired
	private Slack slack;

	public String upload( Object file, String publicId ) {
		try {
			String url = new com.cloudinary.Cloudinary().uploader().upload( file, ObjectUtils.asMap( "public_id", publicId ) ).get( "secure_url" ).toString();

			slack.message( url );

			return url;

		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}
}