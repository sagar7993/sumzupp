package com.sumzupp.backendapp.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.sumzupp.backendapp.enums.S3BucketType;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by akash.mercer on 15-Sep-17.
 */
public class AwsS3FileUploadHelper {
    private static final String TAG = "AwsS3FileUploadHelper : ";

    private static AwsS3FileUploadHelper awsS3FileUploadHelper;

    private static AmazonS3 amazonS3;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    private AwsS3FileUploadHelper() {

    }

    public static AwsS3FileUploadHelper getInstance() {
        if (awsS3FileUploadHelper == null) {
            awsS3FileUploadHelper = new AwsS3FileUploadHelper();

            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(Constants.AWS_ACCESS_KEY_ID, Constants.AWS_SECRET_ACCESS_KEY);

            try {
                amazonS3 = AmazonS3ClientBuilder.standard().withRegion("ap-south-1").withForceGlobalBucketAccessEnabled(true).withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
            } catch (AmazonClientException ace) {
                errorLogger.error(TAG , "Caught an AmazonClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with S3, such as not being able to access the network with error : " + ace.getMessage());
            }
        }

        return awsS3FileUploadHelper;
    }

    public void uploadFile(S3BucketType s3BucketType, File file) {
        //Remember that file name has to be unique because Object key will be used as unique identifier
        try {
            amazonS3.putObject(s3BucketType.getName(), file.getName(), file);
        } catch (AmazonServiceException ase) {
            errorLogger.error(TAG , "Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.\n" +
            "Error Message:    " + ase.getMessage() + "\n" +
            "HTTP Status Code: " + ase.getStatusCode() + "\n" +
            "AWS Error Code:   " + ase.getErrorCode() + "\n" +
            "Error Type:       " + ase.getErrorType() + "\n" +
            "Request ID:       " + ase.getRequestId());

            throw ase;
        } catch (AmazonClientException ace) {
            errorLogger.error(TAG , "Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, such as not being able to access the network with error : " + ace.getMessage());

            throw ace;
        }
    }

}
