package hu.bme.aut.freelancer_spring.model.enums;

/////////////////////////////////////////////////////
// Contains the information about the size and price of the package.
// Has 4 built in presets: S, M, L, XL.
/////////////////////////////////////////////////////

public enum Size {

    //Built in presets, the first 3 parameters represent the 3 dimensions of the package, the 4th is its price.
    S(20,30,40,1000),
    M(40,30,40,1200),
    L(40,60,40,1500),
    XL(120,60,40,2000);

    //The properties of the package
    //x: the X dimension of the package
    //y: the Y dimension of the package
    //z: the Z dimension of the package
    //price: the price of the package
    private final int x, y, z, price;

    //Constructor
    Size(int x, int y, int z, int price) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.price = price;
    }

    //Returns the volume of the package
    public int getCC() {
        return x * y * z;
    }

    //Returns the price of the package
    public int getPrice() {
        return price;
    }
}
