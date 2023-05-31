import cv2
import pybboxes as pbx
import numpy as np

def postProcess(model_result, ori_image_size, class_names, threshold):
    '''
        Result of model detection should be array containing boxes and probabilities each class
    '''
    # Init return array
    result = []

    # transpose result so we can iterate each boxes
    boxes = model_result[0, :].T
    for box in boxes:
        # split box into box coordinate and probabilities
        coco_box = box[0:4]
        probs = box[4:]

        # convert to yolo box and get label and score
        yolo_box = coco_to_yolo(coco_box, ori_image_size)
        label, score = check_probs(probs, threshold= threshold)
        name = class_names[label]

        # if greater than threshold then added into result array
        if label != -1:
            result.append({name: score})
        return result

def coco_to_yolo(coco_box, ori_image_size, input_shape=(640, 640)):
    '''
        Convert coco box format into yolo box format
    '''
    # get the ratio of original shape and model input_shape
    ratio_X = input_shape[0] / ori_image_size[0]
    ratio_Y = input_shape[1] / ori_image_size[1]

    # convert cocobox into yolo box
    yolo_box = np.array(pbx.convert_bbox(coco_box, from_type="coco", to_type="yolo", image_size=input_shape))

    # upscale the image
    yolo_box[0] *= input_shape[0] / ratio_X
    yolo_box[2] *= input_shape[0] / ratio_X
    yolo_box[1] *= input_shape[1] / ratio_Y
    yolo_box[3] *= input_shape[1] / ratio_Y

    # round number into int to draw rectangle
    yolo_box = yolo_box.round().astype(np.int32).tolist()
    w, h = yolo_box[2], yolo_box[3]

    # adjust the box into x0, y0, x1, y1
    yolo_box[2] = yolo_box[0] - yolo_box[2]
    yolo_box[3] = yolo_box[1] - yolo_box[3]

    return yolo_box, (w, h)

def check_probs(probs, threshold = 0.5):
    '''
        return label of highest probability and greater than threshold
        if not pass the threshold will return -1
    '''
    probs = np.array(probs)
    status = probs > threshold
    if True in status:
        idx_argmax = np.argmax(probs)
        return idx_argmax, round(probs[idx_argmax], 3)
    else:
        return -1, 0