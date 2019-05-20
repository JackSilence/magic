package magic.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class Utils {
	public static String decode( String str ) {
		return new String( Base64.getDecoder().decode( str ) );
	}

	public static String getResourceAsString( String path ) {
		try {
			return IOUtils.toString( Utils.class.getResource( path ), StandardCharsets.UTF_8 );

		} catch ( IOException e ) {
			throw new RuntimeException( "Path: " + path, e );
		}
	}

	public static String upload( Object file, String publicId ) throws IOException {
		return new Cloudinary().uploader().upload( file, ObjectUtils.asMap( "public_id", publicId ) ).get( "secure_url" ).toString();
	}
}