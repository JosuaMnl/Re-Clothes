# Re-Clothes

<h2> Project Brief (Backgrounder </h2>
<p>
Machine Learning:
We have developed a fabric-type classification model using TensorFlow's Convolutional Neural Network. This model's purpose is to classify inputted images into nine different classes: cotton, terrycloth, denim, fleece, nylon, polyester, silk, viscose, and wool. The model demonstrates excellent performance, achieving an accuracy rate of approximately 90%. It is capable of accurately distinguishing between the majority and minority classes.
Additionally, we have constructed a second model specifically for detecting defects in cloth products. The defects are categorized into three types: knots, stains, and holes. This model exhibits a high confidence score when identifying knots and stains. However, it demonstrates a lower confidence score when detecting holes in products.
</p>
  
<p>Mobile Development
We are a Mobile Development team, creating "ReClothes", an application built on Kotlin and Jetpack Compose, utilizing the Model-View-ViewModel (MVVM) architecture. The app facilitates the sale of used clothes while promoting sustainability. Besides user authentication and profile personalization, the application hosts unique features like Do-It-Yourself (DIY) Recommendations and a Medals Gamification system to encourage user engagement.
A standout feature of ReClothes is a machine learning functionality that assesses the quality and identifies the fabric of used clothing. This innovative feature, powered by a cloud-deployed model from our Cloud Computing team, streamlines the user experience. All sales data is stored securely via APIs developed by our Cloud Computing team, ensuring seamless operation and making ReClothes a forefront runner in bridging technology and sustainability.
<p>Cloud Computing
We've deployed two models from our Machine Learning team using Vertex AI, as some requests require over 1.5 MB. To bypass size limits, we utilize a private endpoint. We then use a cloud function to trigger the model. Since the private endpoint is within the VPC and the cloud function is external, a VPC connector is needed to connect the services.
For the API backend, we employ Laravel and deploy the API to Cloud Run, which offers cost efficiency, enhanced security, and improved performance. In total, we have effectively combined various technologies to ensure seamless functionality and meet the demands of our deployment.</p>

</p>

### CC Stuff
* backend-api with continuius deployment [mrdotss/reclothes-api](https://github.com/mrdotss/reclothes-api)
