package application.ui.components.custom;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.server.streams.InMemoryUploadHandler;
import com.vaadin.flow.server.streams.UploadHandler;

public class PhotoUpload extends CustomField<byte[]> {

    Upload photoUpload;

    public PhotoUpload(String label) {
        setLabel(label);
        photoUpload = new Upload(getPhotoDataHandler());
        configurePhotoUpload();
        add(photoUpload);
    }

    private UploadHandler getPhotoDataHandler() {
        InMemoryUploadHandler photoDataHandler = UploadHandler
            .inMemory((memory, data) -> {
                setValue(data);
                Notification.show("Upload Successful!", 3000, Notification.Position.MIDDLE);
            })
            .whenComplete(success -> {
                if (!success) {
                    setValue(null);
                }
            });

        return photoDataHandler;
    }

    private void configurePhotoUpload() {
        photoUpload.setAcceptedFileTypes("image/jpeg", "image/png");
        photoUpload.setMaxFiles(1);
        photoUpload.setMaxFileSize(1024 * 1024 * 5); //5 MB

        photoUpload.addFileRejectedListener(rejected -> {
            setValue(null);
            Notification.show(rejected.getErrorMessage());
        });

        photoUpload.addFileRemovedListener(removed -> {
            setValue(null);
            Notification.show("Upload Removed", 3000, Notification.Position.MIDDLE );
        });
    }

    @Override
    protected byte[] generateModelValue() {
        return getValue();
    }

    @Override
    protected void setPresentationValue(byte[] bytes) {
        if (bytes == null) {
            photoUpload.clearFileList();
        }
    }
}
