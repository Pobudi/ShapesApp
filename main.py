from flask import Flask, request, jsonify
import matplotlib.pyplot as plt
from tensorflow import keras
import numpy as np
import cv2
import time

_name_ = '_main_'
app = Flask(_name_)

@app.route('/')
def home():
    return "Serwer API Flask działa!"

@app.route('/api/data', methods=['GET', 'POST'])
def handle_data():
    if request.method == 'POST':
        data = request.json

        x = data['x']
        y = data['y']
        
        for i in range(0, len(y)):
            y[i] = -y[i]


        fig = plt.figure(figsize=(70/100, 70/100))
        fig_name = f'foo{time.time()}'
        plt.plot(x,y)
        plt.axis('off')
        fig.savefig(f"{fig_name}.png", dpi=100)

        x = np.array([cv2.imread(f"{fig_name}.png", cv2.IMREAD_GRAYSCALE)])
        x = x.reshape(-1, 70, 70, 1)
        model = keras.models.load_model('RealArtificialIntelligence.h5')

        
        realAIResponse = np.argmax(model.predict(x))
        print(realAIResponse)
        return jsonify({"response": f'{realAIResponse}'})


if _name_ == '_main_':
    app.run(debug=False, host='0.0.0.0', port=5000)



# from flask import Flask, request, jsonify
# import matplotlib.pyplot as plt
# from tensorflow import keras
# import numpy as np
# import cv2
# import time

# _name_ = '_main_'
# app = Flask(_name_)

# @app.route('/')
# def home():
#     return "Serwer API Flask działa!"

# @app.route('/api/data', methods=['GET', 'POST'])
# def handle_data():
#     if request.method == 'POST':
#         data = request.json

#         x = data['x']
#         y = data['y']
        
#         for i in range(0, len(y)):
#             y[i] = -y[i]
#         image_size = (70, 70)
#         image = np.zeros(image_size, dtype=np.uint8)  # Tworzymy czarny obraz o rozmiarze 500x500

#         # Ustawiamy skalowanie współrzędnych, aby pasowały do rozmiarów obrazu
#         x_max, y_max = max(x), max(y)
#         x_min, y_min = min(x), min(y)

#         # Normalizujemy współrzędne do zakresu obrazu
#         scaled_x = np.array([(i - x_min) / (x_max - x_min) * (image_size[1] - 1) for i in x], dtype=int)
#         scaled_y = np.array([(i - y_min) / (y_max - y_min) * (image_size[0] - 1) for i in y], dtype=int)

#         # Nanosi punkty na obraz (kolor biały dla punktów)
#         for i in range(len(scaled_x)):
#             image[scaled_y[i], scaled_x[i]] = 255  # Biały punkt na czarnym tle

#         # Zapisz obraz
#         fig_name = f'foo{time.time()}'

#         cv2.imwrite(f'{fig_name}.png', image)
#         img = cv2.imread(f'{fig_name}.png', cv2.IMREAD_GRAYSCALE)
#         img = -img
#         print(img.shape)
#         # h, w = img.shape
#         # if h != w:
#         #     size = max(h, w)
#         #     padded_img = np.zeros((size, size), dtype=np.uint8)
#         #     padded_img[:h, :w] = img
#         #     img = padded_img

#         # img = cv2.resize(img, (70, 70), interpolation=cv2.INTER_AREA)


#         # print(img)
#         cv2.imwrite('temp.png', img)

#         img = img / 255

#         img = np.expand_dims(img, axis=-1)  # Add channel dimension (70, 70, 1)
#         img = np.expand_dims(img, axis=0)  # Add batch dimension (1, 70, 70, 1

#         model = keras.models.load_model('RealArtificialIntelligence.h5')
#         realAIResponse = np.argmax(model.predict(img))
#         print(model.predict(img))

#         print(realAIResponse)
        
#         plt.clf()
        
#         return jsonify({"response": f'{realAIResponse}'})

#     if request.method == 'GET':
#         return jsonify({"message": "To jest odpowiedź na zapytanie GET"})

# if _name_ == '_main_':
#     app.run(debug=False, host='0.0.0.0', port=5000)

