 public enum PawnType {
    BLACK(2), RED(4);

     private final int pawnType;

     PawnType(int pawnType) {
         this.pawnType = pawnType;
     }

     public int getValue(){return pawnType;}
 }
