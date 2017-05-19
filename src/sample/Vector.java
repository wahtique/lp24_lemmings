package sample;

class Vector {
    private double x;
    private double y;

    public Vector(double _x, double _y)
    {
        x= _x;
        y= _y;
    }

    public double length(){
        return Math.sqrt (x*x +y*y);
    }

    public double dot(Vector vec){
        return x*vec.getX()+y*vec.getY();
    }

    public Vector mulScal(double d){ return new Vector(this.x*d,this.y*d); }

    public boolean isPerpendicular(Vector vec){
        if(dot(vec) == 0){
            return true;
        }else{
            return false;
        }
    }

    public String toString(){
        return (" [" +x +" , " +y +" ] lenght: " +length());
    }

    public Vector add(Vector vec){
        Vector v = new Vector(x + vec.getX(), y + vec.getY());
        return v;
    }

    public void setX(double _x){
        x = _x;
    }
    public void setY(double _y){
        y = _y;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }


}