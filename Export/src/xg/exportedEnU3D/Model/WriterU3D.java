package xg.exportedEnU3D.Model;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
//import java.util.HashMap;

//import xg.exportedEnU3D.newTemporaire.Geometry;

public class WriterU3D {
//	protected HashMap<Geometry, String>	geometryNameMap = null;
	public static int writeDataBlock(DataBlock b, WritableByteChannel o) throws IOException {
		int dataSize = (int) Math.ceil(b.getDataSize() / 4.0); // include padding
		int metaDataSize = (int) Math.ceil(b.getMetaDataSize() / 4.0); // include padding

		int blockLength = (int) (12 + 4 * (dataSize + metaDataSize));

		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024).order(LITTLE_ENDIAN);
		if (buffer.capacity() < blockLength) {
			buffer = ByteBuffer.allocate(blockLength);
			buffer.order(LITTLE_ENDIAN);
		}
		buffer.position(0);
		buffer.limit(blockLength);

		buffer.putInt((int) b.getBlockType());
		buffer.putInt((int) b.getDataSize());
		buffer.putInt((int) b.getMetaDataSize());

		for (int i = 0; i < dataSize; i++)
			buffer.putInt((int) b.getData()[i]);
		for (int i = 0; i < metaDataSize; i++)
			buffer.putInt((int) b.getMetaData()[i]);
		buffer.rewind();
		o.write(buffer);
		
		return blockLength;
	}
	
	public static int jApprendAEcrire(DataBlock b, WritableByteChannel o) throws IOException {
		int dataSize = (int) Math.ceil(b.getDataSize() / 4.0); // include padding
		int metaDataSize = (int) Math.ceil(b.getMetaDataSize() / 4.0); // include padding
System.out.println("controle dataSize ="+dataSize+"\n metadataSize ="+metaDataSize);
		int blockLength = (int) (4 * (dataSize + metaDataSize));

		ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024).order(LITTLE_ENDIAN);
		if (buffer.capacity() < blockLength) {
			buffer = ByteBuffer.allocate(blockLength);
			buffer.order(LITTLE_ENDIAN);
		}
		buffer.position(0);
		buffer.limit(blockLength);

//		buffer.putInt((int) b.getBlockType());
//		buffer.putInt((int) b.getDataSize());
//		buffer.putInt((int) b.getMetaDataSize());

		for (int i = 0; i < dataSize; i++) {
			buffer.putInt((int) b.getData()[i]);
			System.out.println("\n check data ="+b.getData()[i]);
		}
		for (int i = 0; i < metaDataSize; i++)
			buffer.putInt((int) b.getMetaData()[i]);
		buffer.rewind();
		o.write(buffer);
		
		return blockLength;
	}

//	protected DataBlock getPointSetDeclaration(PointSet g) {
//		// Only partially supported by adobe reader
//		BitStreamWrite w = new BitStreamWrite();
//		w.WriteString(geometryNameMap.get(g));
//		w.WriteU32(0);
//
//		// reserved
//		w.WriteU32(0);
//		// number of points
//		w.WriteU32(0);
//		w.WriteU32(g.getNumPoints());
//
//		// vertex normals
//		DoubleArrayArray nData = (DoubleArrayArray)g.getVertexAttributes(NORMALS);
//		if (nData != null) {
//			w.WriteU32(nData.size());
//		} else {
//			w.WriteU32(0);
//		}
//		// diffuse colors
//		DoubleArrayArray vColors = (DoubleArrayArray)g.getVertexAttributes(COLORS);
//		if (vColors != null) {
//			w.WriteU32(vColors.size());
//		} else {
//			w.WriteU32(0);
//		}
//		//specular colors
//		w.WriteU32(0);
//
//		// texture coordinates
//		w.WriteU32(0);
//
//		// shading count
//		w.WriteU32(1);
//		// standard shading
//		w.WriteU32(0x00000000);
//		w.WriteU32(0);
//		w.WriteU32(0);
//
//		// not relevant for point sets
//		w.WriteU32(1000);
//		w.WriteU32(1000);
//		w.WriteU32(1000);
//
//		// Resource Inverse Quantization
//		float m_fQuantPosition = (float) Math.pow(2.0,18.0);
//		m_fQuantPosition = (float) Math.max(m_fQuantPosition, fLimit);
//		float m_fQuantNormal = (float) Math.pow(2.0,14.0);
//		float m_fQuantTexCoord = (float) Math.pow(2.0,14.0);
//		float m_fQuantDiffuseColor  = (float) Math.pow(2.0,14.0);
//		float m_fQuantSpecularColor  = (float) Math.pow(2.0,14.0);
//
//		w.WriteF32(1.0f / m_fQuantPosition);
//		w.WriteF32(1.0f / m_fQuantNormal);
//		w.WriteF32(1.0f / m_fQuantTexCoord);
//		w.WriteF32(1.0f / m_fQuantDiffuseColor);
//		w.WriteF32(1.0f / m_fQuantSpecularColor);
//
//		// Resource parameters
//		for (int i = 0; i < 3; i++)
//			w.WriteF32(1.0f);
//
//		// no bones
//		w.WriteU32(0);
//
//		DataBlock b = w.GetDataBlock();
//		b.setBlockType(TYPE_POINT_SET_DECLARATION);
//		return b;
//	}
}
