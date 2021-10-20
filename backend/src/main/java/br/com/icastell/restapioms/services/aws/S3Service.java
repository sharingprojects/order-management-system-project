package br.com.icastell.restapioms.services.aws;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.icastell.restapioms.services.exceptions.FileException;



@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	
	// to do upload to S3
		//return a URI: web address of the newly created resource
		public URI upLoadFile(MultipartFile multipartFile) {
			try {
			InputStream is = multipartFile.getInputStream();
			String fileName = multipartFile.getOriginalFilename();		
			String contentType = multipartFile.getContentType();
			return upLoadFile(is, fileName, contentType);
			
			} catch (IOException e) {
				throw new FileException("Erro de IO: " + e.getMessage());
			}	
		}
					
		public URI upLoadFile(InputStream is, String fileName, String contentType) {
			
			try {			
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(contentType);
			
				LOG.info("Start upload...");
				s3Client.putObject(bucketName, fileName, is, metadata);
				LOG.info("Finish upload.");		
				return s3Client.getUrl(fileName, fileName).toURI();
				
			} catch (URISyntaxException e) {
				throw new FileException("Erro ao converter URL para URI");
			}
		}
			
}
