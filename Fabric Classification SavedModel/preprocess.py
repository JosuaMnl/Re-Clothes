import tensorflow as tf
import cv2

def preprocess(input_image):
    ''' This function prepares the input image and preprocess it 
    including resizing, normalizing, and converting to tensor '''

    # Read image
    preprocessed_image = cv2.imread(input_image)
    preprocessed_image = cv2.cvtColor(preprocessed_image, cv2.COLOR_BGR2RGB)

    ## Apply preprocessing
    # Resize the image
    preprocessed_image = tf.image.resize(preprocessed_image, [200, 200])
    # Normalize image
    preprocessed_image = preprocessed_image / 255.0
    # Expand dimension
    preprocessed_image = tf.expand_dims(preprocessed_image, axis=0)

    return preprocessed_image