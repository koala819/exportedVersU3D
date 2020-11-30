package xg.exportedEnU3D.newTemporaire;

public class PointSet {
//	protected DataListSet vertexAttributes;
//	  
//	  private static int UNNAMED_ID;
//	  
//	  public PointSet()
//	  {
//	    this(0);
//	  }
//
//	  public PointSet(int numPoints)
//	  {
//		this("point-set "+(UNNAMED_ID), numPoints);
//	 }
//
//	  public PointSet(String name)
//	  {
//		this(name, 0);
//	  }
//
//	  public PointSet(String name, int numPoints)
//	  {
//		super(name);
//		vertexAttributes= new DataListSet(numPoints);
//		geometryAttributeCategory.put( Geometry.CATEGORY_VERTEX, vertexAttributes );
//	 }
//
//	  /**
//	   * The number of vertices defines the length of all data lists associated
//	   * with vertex attributes.
//	   */
//	  public int getNumPoints() {
//		return getNumEntries( vertexAttributes );
//	  }
//
//	  /**
//	   * Sets the number of vertices, implies removal of all previously defined
//	   * vertex attributes.
//	   * @param numVertices the number of vertices to set >=0
//	   */
//	  public void setNumPoints(int numVertices) {
//		  setNumEntries( vertexAttributes, numVertices );
//	  }
//
//	  /**
//	   * Returns a read-only view to all currently defined vertex attributes.
//	   * You can copy all currently defined vertex attributes to another
//	   * PointSet using
//	   * <code>target.setVertexAttributes(source.getVertexAttributes())</code>
//	   * These attributes are copied then, not shared. Thus modifying either
//	   * source or target afterwards will not affect the other.
//	   * @see setVertexAttributes(DataListSet)
//	   * @see getGeometryAttributes()
//	   */
//	  public DataListSet getVertexAttributes() {
//		  return getAttributes(vertexAttributes);
//	  }
//
//	  public DataList getVertexAttributes(Attribute attr) {
//	    return getAttributes(vertexAttributes,attr);
//	  }
//
//	  public void setVertexAttributes(DataListSet dls) {
//		  setAttributes(CATEGORY_VERTEX, vertexAttributes,dls);
//	  }
//
//	  public void setVertexAttributes(Attribute attr, DataList dl) {
//		  setAttributes(CATEGORY_VERTEX, vertexAttributes, attr, dl );
//	  }
//
//	  public void setVertexCountAndAttributes(Attribute attr, DataList dl) {
//	    setCountAndAttributes(CATEGORY_VERTEX, vertexAttributes, attr, dl );
//	  }
//
//	  public void setVertexCountAndAttributes(DataListSet dls) {
//	    setCountAndAttributes(CATEGORY_VERTEX, vertexAttributes,dls);
//	  }
//
//	  
//	  public void accept(SceneGraphVisitor v)
//	  {
//	    startReader();
//	    try {
//	      v.visit(this);
//	    } finally {
//	      finishReader();
//	    }
//	  }
//	  static void superAccept(PointSet ps, SceneGraphVisitor v)
//	  {
//	    ps.superAccept(v);
//	  }
//	  private void superAccept(SceneGraphVisitor v)
//	  {
//	    super.accept(v);
//	  }
}
