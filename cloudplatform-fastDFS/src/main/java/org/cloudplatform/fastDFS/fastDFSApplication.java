package org.cloudplatform.fastDFS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.tobato.fastdfs.FdfsClientConfig;

@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
public class fastDFSApplication {

	public static void main(String[] args) {
		SpringApplication.run(fastDFSApplication.class, args);
	}
}
