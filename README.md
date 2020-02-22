# PRODUCT DEMO APPLICATION
This is a Rest Api Application to retrieve, update Product Details with pricing details.

## Run the APP
    gradle bootRun

## Build the App
    gradle clean build

## Get Product Details
### Overview
> This Operation provides product price and name related to the product Id provided.
> Get the product price from DB and product details from an external API (http://redsky.target.com/v2) in Parallel.
### Service Contract:
> Service contract is available on Swagger page:
>[Get Product Details](http://localhost:8080/swagger-ui.html#/product-controller/getProductUsingGET)


## Update Product Price
### Operation Overview
> This Operation allows user to Update the product price of an existing Product.
### Service Contract:
> Service contract is available on Swagger page:
>[Update Product Price](http://localhost:8080/swagger-ui.html#/product-controller/updateProductUsingPUT)


## Add Product Price
### Operation Overview
> This Operation allows user to Add the product price of an Product.
### Service Contract:
> Service contract is available on Swagger page:
>[Add Product Price](http://localhost:8080/swagger-ui.html#/product-controller/createProductUsingPOST)


## Delete Product Price
### Operation Overview
> This Operation allows user to Delete the product price of an Product.
### Service Contract:
> Service contract is available on Swagger page:
>[Delete Product Price](http://localhost:8080/swagger-ui.html#/product-controller/deleteProductUsingDELETE)

