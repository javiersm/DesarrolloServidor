package PLSQL;

public class BORRAREJEMPLAR {

	public BORRAREJEMPLAR() {
	}
	/*
	CREATE OR REPLACE PROCEDURE BORRAREJEMPLAR (P_IDEJEMPLAR IN VARCHAR2 ) AS 
	BEGIN
	  DBMS_OUTPUT.PUT_LINE('EJEMPLAR A BORRAR: ' || P_IDEJEMPLAR);
	  DELETE FROM DEVOLUCION WHERE IDEJEMPLAR = P_IDEJEMPLAR;
	  DELETE FROM EJEMPLAR WHERE IDEJEMPLAR = P_IDEJEMPLAR;
	  COMMIT;
	END BORRAREJEMPLAR;
	*/
}