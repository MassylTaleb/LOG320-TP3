 public enum CellType {
    BLACK(2), RED(4), EMPTY(0), FORBIDDEN(-1);

     private final int pawnType;

     CellType(int pawnType) {
         this.pawnType = pawnType;
     }

     public int getValue(){return pawnType;}
 }
