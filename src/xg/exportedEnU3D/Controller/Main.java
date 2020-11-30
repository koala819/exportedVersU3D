package xg.exportedEnU3D.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import xg.exportedEnU3D.Model.BitStreamWrite;
//import xg.exportedEnU3D.Model.EcrireEnBinaire;
import xg.exportedEnU3D.Model.DataBlock;
import xg.exportedEnU3D.Model.WriterU3D;
import static xg.exportedEnU3D.Model.U3DConstants.*;

public class Main {
	

	public static void main(String[] args) {
		File fichierDeSortieU3D = new File("c:/TMP/unFondBleu.u3d");
		try
		{
			BitStreamWrite fluxPourEcrireEnBinaire = new BitStreamWrite();
			/*-------------------
			 * File Header Block 
			 *-------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0x00443355);						//BLOCKTYPE			
			//(2x2)+(3x4)+8 = 24
			fluxPourEcrireEnBinaire.WriteU32(24);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteI16((short)0);							//MAJOR VERSION				Cette valeur doit être 0 selon la spécification officielle.
			fluxPourEcrireEnBinaire.WriteI16((short)0);							//MINOR VERSION				Il peut s'agir de n'importe quelle valeur, mais pour des raisons de santé mentale, nous utiliserons la valeur 0.
			fluxPourEcrireEnBinaire.WriteU32(0);								//PROFILE IDENTIFIER
			//(4x3)+(2x2)+(2x4)+8+4 = 36
			fluxPourEcrireEnBinaire.WriteU32(36);								//DECLARATION SIZE			includes the size of all blocks in File Header Block
			fluxPourEcrireEnBinaire.WriteU64(488);								//FILE SIZE					size of all blocks in File
			fluxPourEcrireEnBinaire.WriteU32(106);								//CHARACTER ENCODING
			
			/*--------------------------------
			 *     Priority Update 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF15);						//BLOCKTYPE 
			fluxPourEcrireEnBinaire.WriteU32(4);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteU32(0);								//NEW PRIORITY
			
			
			/*--------------------------------
			 *     Modifier Chain Block 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF14);						//BLOCKTYPE 
			//???(2+9)+(4x3)+1 = 24 ???
			fluxPourEcrireEnBinaire.WriteU32(136);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			//WriteString : U16:longueur + chaque caractère U8 <=> VcgMesh01 = (2+9)=11
			fluxPourEcrireEnBinaire.WriteString("VcgMesh01");					//MODIFIER CHAIN NAME
			fluxPourEcrireEnBinaire.WriteU32(0);								//MODIFIER CHAIN TYPE
			fluxPourEcrireEnBinaire.WriteU32(0);								//MODIFIER CHAIN ATTRIBUTES
			fluxPourEcrireEnBinaire.WriteU8((short) 0);							//MODIFIER CHAIN PADDING (variable de 0 à 3 octets)
			fluxPourEcrireEnBinaire.WriteU32(1);								//MODIFIER COUNT
			
			/*-----------------------
			*    Model Node Block 
			*-----------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF22 );					//BLOCKTYPE
			//(2+9)+4+2+(4x4)+(4x4)+(4x4)+(4x4)+(2+11)+4 = 98  (on ne compte pas le data padding 'U16')
			fluxPourEcrireEnBinaire.WriteU32(98);							//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);							//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteString("VcgMesh01");				//MODEL NODE NAME
			fluxPourEcrireEnBinaire.WriteU32(1);							//PARENT NODE COUNT
			fluxPourEcrireEnBinaire.WriteU16(0);							//???p36 16_pointversParent_NodeTransform_Matrix_Element???
			
			// Col 1
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT
			
			// Col 2
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	

			// Col 3
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	

			// Col 4
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT
			fluxPourEcrireEnBinaire.WriteF32(0.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT		
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//PARENT NODE TRANSFORM MATRIX ELEMENT	
			
			fluxPourEcrireEnBinaire.WriteString("MyVcgMesh01");				//MODEL RESOURCE NAME
			fluxPourEcrireEnBinaire.WriteU32(1);							//MODEL VISIBILITY
			fluxPourEcrireEnBinaire.WriteU16(0);							//DATA PADDING
			
			/*--------------------------------
			 *     Modifier Chain Block 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF14);						//BLOCKTYPE 
			//???(2+11)+(4x3)+1+2 = 28 ???
			fluxPourEcrireEnBinaire.WriteU32(160);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);	    						//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteString("MyVcgMesh01");					//MODIFIER CHAIN NAME
			fluxPourEcrireEnBinaire.WriteU32(1);								//MODIFIER CHAIN TYPE
			fluxPourEcrireEnBinaire.WriteU32(0);								//MODIFIER CHAIN ATTRIBUTES
			fluxPourEcrireEnBinaire.WriteU8((short) 0);							//MODIFIER CHAIN PADDING (variable de 0 à 3 octets)
			fluxPourEcrireEnBinaire.WriteU16(0);								//MODIFIER CHAIN PADDING (variable de 0 à 3 octets)
			fluxPourEcrireEnBinaire.WriteU32(1);								//MODIFIER COUNT			
			
			/*----------------------
			* CLOD Mesh Declaration 
			*-----------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF31);						//BLOCKTYPE
			//(2+11)+(26x4)+1 = 118 (on ne compte pas le data padding 'U16')
			fluxPourEcrireEnBinaire.WriteU32(118);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteString("MyVcgMesh01");					//MESH NAME
			fluxPourEcrireEnBinaire.WriteU32(0);								//CHAIN INDEX
			//MAX MESH DESCRIPTION 
			fluxPourEcrireEnBinaire.WriteU32(1);								//MESH ATTRIBUTES		0x00000001 – Exclude Normals: The faces in the mesh do not have a normal index at each corner
			fluxPourEcrireEnBinaire.WriteU32(1);								//FACE COUNT			number of faces in the mesh
			fluxPourEcrireEnBinaire.WriteU32(3);								//POSITION COUNT		number of positions in the position array
			fluxPourEcrireEnBinaire.WriteU32(0);								//NORMAL COUNT			number of normals in the normal array
			fluxPourEcrireEnBinaire.WriteU32(0);								//DIFFUSE COLOR COUNT	number of colors in the diffuse color array
			fluxPourEcrireEnBinaire.WriteU32(0);								//SPECULAR COLOR COUNT	number of colors in the specular color array
			fluxPourEcrireEnBinaire.WriteU32(0);								//TEXTURE COORD COUNT	number of texture coordinates in the texture coordinate array
			fluxPourEcrireEnBinaire.WriteU32(1);								//SHADING COUNT			number  of  shading  descriptions  used  in  the  mesh.  Each  shading description corresponds to one shader list in the shading group
			//***SHADDING DESCRIPTION***
			fluxPourEcrireEnBinaire.WriteU32(0);								//SHADING ATTRIBUTES				0x00000000 – The shader list uses neither diffuse colors nor specular colors
			fluxPourEcrireEnBinaire.WriteU32(0);								//TEXTURE LAYER COUNT				number of texture layers used by this shader list
			fluxPourEcrireEnBinaire.WriteU32(0);								//**Texture Coord Dimensions**		number of dimensions in the texture coordinate vector. The texture coordinate vector can have 1, 2, 3, or 4 dimensions
			//FIN 9.6.1.1.3
			
//DEBUT MODIF			
			//CLOD Description
			fluxPourEcrireEnBinaire.WriteU32(0);								//MINIMUM RESOLUTION		shall be the number of positions in the base mesh
			fluxPourEcrireEnBinaire.WriteU32(3);								//FINAL MAXIMUM RESOLUTION	shall be the number of positions in the Max Mesh Description	
			
			
			//resource description
			fluxPourEcrireEnBinaire.WriteU32(500);								//Position Quality Factor	4 octets, U32	quality factor associated with quantization of positions
			fluxPourEcrireEnBinaire.WriteU32(1000);								//Normal Quality Factor		4 octets, U32	quality factor associated with quantization of normal vectors
			fluxPourEcrireEnBinaire.WriteU32(1000);								//Texture coord
			
			//Inverse Quantization
//convertir u32 en f32
			fluxPourEcrireEnBinaire.WriteU32(973107651);						
			//4x même valeurs 00 00 80 38
			fluxPourEcrireEnBinaire.WriteU32(947912704);
			fluxPourEcrireEnBinaire.WriteU32(947912704);
			fluxPourEcrireEnBinaire.WriteU32(947912704);
			fluxPourEcrireEnBinaire.WriteU32(947912704);
			
			
			//resource Parameter
			fluxPourEcrireEnBinaire.WriteF32(0.9f);
			fluxPourEcrireEnBinaire.WriteF32(0.5f);
			fluxPourEcrireEnBinaire.WriteF32(0.985f);
			
			
			//Skeleton Description
			fluxPourEcrireEnBinaire.WriteU32(0);							//Bone count
			fluxPourEcrireEnBinaire.WriteU16(0);
			fluxPourEcrireEnBinaire.WriteU8((short) 0);
			
			/*--------------------------------
			 *     Priority Update 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF15);						//BLOCKTYPE 
			fluxPourEcrireEnBinaire.WriteU32(4);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteU32(256);								//NEW PRIORITY
			
			/*-----------------------------------
			* CLOD  Progressive Mesh Continuation 
			*------------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF3C);						//BLOCKTYPE
			//(2+9)+(15x4)+(4x2)+(7x1) = 86
			fluxPourEcrireEnBinaire.WriteU32(86);								//DATA SIZE
			fluxPourEcrireEnBinaire.WriteU32(0);								//META DATA SIZE
		//--DATA--
			fluxPourEcrireEnBinaire.WriteString("MyVcgMesh01");					//MESH NAME
			fluxPourEcrireEnBinaire.WriteU32(0);								//CHAIN INDEX
			//RESOLUTION UPDATE RANGE
			fluxPourEcrireEnBinaire.WriteU32(0);								//START RESOLUTION
			fluxPourEcrireEnBinaire.WriteU32(3);								//END RESOLUTION
			//RESOLUTION UPDATE
			fluxPourEcrireEnBinaire.WriteU32(0);								//SPLIT POSITION INDEX
				//NEW DIFFUSE COLOR INFO
			fluxPourEcrireEnBinaire.WriteU16(0);								//NEW DIFFUSE COLOR COUNT								
			fluxPourEcrireEnBinaire.WriteU8((short) 0);							//DIFFUSE COLOR DIFFERENCE SIGNS
			fluxPourEcrireEnBinaire.WriteU32(0);								//
			fluxPourEcrireEnBinaire.WriteU32(33554432);							//
			fluxPourEcrireEnBinaire.WriteU32(2041);								//DIFFUSE COLOR RED
			fluxPourEcrireEnBinaire.WriteU32(2041);								//DIFFUSE COLOR GREEN
			fluxPourEcrireEnBinaire.WriteU32(2041);								//DIFFUSE COLOR BLUE
			fluxPourEcrireEnBinaire.WriteU32((long) 1256);								//DIFFUSE COLOR ALPHA
				//SPECULAR COLOR INFO
//			//New Diffuse Color Info p67
//			fluxPourEcrireEnBinaire.WriteU16(0);								//NewDiffuse Color Count 
//			fluxPourEcrireEnBinaire.WriteU8((short) 2);							//Diffuse Color Difference Signs 0x02 – Sign bit for Diffuse Color Difference Green p67
//			fluxPourEcrireEnBinaire.WriteU32(2041);								//Diffuse Color Difference Red 
//			fluxPourEcrireEnBinaire.WriteU32(2041);								//Diffuse Color Difference Green
//			fluxPourEcrireEnBinaire.WriteU32(2041);								//Diffuse Color Difference Blue
//			fluxPourEcrireEnBinaire.WriteU8((short) 127);						//Diffuse Color Difference Alpha	U32
//			fluxPourEcrireEnBinaire.WriteU8((short) 20);						//Diffuse Color Difference Alpha	U32
//			fluxPourEcrireEnBinaire.WriteU8((short) 14);						//Diffuse Color Difference Alpha	U32
//			fluxPourEcrireEnBinaire.WriteU8((short) 150);						//Diffuse Color Difference Alpha	U32
//			//New Specular Color Info p68
//			fluxPourEcrireEnBinaire.WriteU16(1);								//New Specular Color Count
////			fluxPourEcrireEnBinaire.WriteU8((short) 0);							//Specular Color Difference Signs
//			fluxPourEcrireEnBinaire.WriteU32(0);
//			fluxPourEcrireEnBinaire.WriteU32(0);
//			fluxPourEcrireEnBinaire.WriteU32(274);
//			fluxPourEcrireEnBinaire.WriteU32(0);
//			//
//			fluxPourEcrireEnBinaire.WriteU16(33408);
//			fluxPourEcrireEnBinaire.WriteU8((short) 34);
//			fluxPourEcrireEnBinaire.WriteU32(3378391);
//			fluxPourEcrireEnBinaire.WriteU8((short) 144);
//			fluxPourEcrireEnBinaire.WriteU16(1636);
//			fluxPourEcrireEnBinaire.WriteU32(0);
			
			
			// Ecriture blockType & données
			DataBlock monPremierBloc = fluxPourEcrireEnBinaire.GetDataBlock();
			long dataSizeHeaderBlock = monPremierBloc.getDataSize(); // **PLUS UTILE**on soustrait les 12 octets du HeaderBlock (BlockType/DataSize/MetaDataSize)
			System.out.println("Ligne67 :: DataSize = " + dataSizeHeaderBlock); 
			FileOutputStream fluxDeSortiePourEcrireLeFichierU3D = new FileOutputStream(fichierDeSortieU3D);
			WritableByteChannel canalDEcritureEnBinaire = Channels.newChannel(fluxDeSortiePourEcrireLeFichierU3D);
//			monPremierBloc.setBlockType(TYPE_FILE_HEADER);
//			WriterU3D.writeDataBlock(monPremierBloc, canalDEcritureEnBinaire);
			WriterU3D.jApprendAEcrire(monPremierBloc, canalDEcritureEnBinaire);

			//FIN
			fluxDeSortiePourEcrireLeFichierU3D.close();
			System.out.println("Travail effectué avec succès : " + fichierDeSortieU3D);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}


//
//
///*  -----------------
// * | BLOCK STRUCTURE |	All block types have the same basic structure.
// *  -----------------
// *  The Block Type, Data Size, and Meta Data Size determine how the remainder of the block is interpreted by the loader.
// *  Data Padding and Meta Data Padding fields are used to keep 32-bit alignment relative to the start of the File Header Block.
// *  The start of the Block Type field, Data section and Meta Data section are all 32-bit aligned.
// * 
// * BLOCK TYPE ----> DATA SIZE ----> META DATA SIZE ----> DATA ----> DATA PADDING ----> META DATA ----> META DATA PADDING
// * 
// * BLOCK TYPE 	 		U32 <=>	identifies  the  type  of  object  associated  with  this  block
// * DATA SIZE  			U32 <=> size of the Data section in bytes. Data Size does not include the size of the Data Padding
// * META DATA SIZE		U32 <=> size of the Meta Data section in bytes. Meta Data Size does not include the size of the Meta Data Padding 
// * DATA					Data Size bytes of data. The interpretation of the Data section depends on the Block Type. 
// * DATA PADDING			variable size field <=> Zero to three bytes are inserted to maintain 32-bit alignment for the start of the Meta Data section. The value of the padding bytes is 0x00.
// * META DATA 			U32 <=> Meta Data is Meta Data Size bytes of data. The Meta Data section contains a sequence of Key/Value pairs. 
// * 								The interpretation of the content of the Key/Value pairs is outside the scope of this specification.
// * 						KEY/VALUE PAIR COUNT -------------------------------------------------------------------------->|
// * 																		----> VALUE STRING ---------------------------->|
// * 						KEY/VALUE PAIR ATTRIBUTES ----> KEY STRING ----|												|---->
// * 							/|\											----> BINARY VALUE SIZE ----> BINARY VALUE ---->|
// * 							 |-----------------KEY/VALUE PAIR COUNT--------------------------------------------------<<<| 	
// * 								key/value pair count			U32 <=> the number of Key/Value pairs in this Meta Data section
// * 								key/value pair attributes		U32 <=> indicate  formatting  options  for  the  Key/Value  pair.  The following attribute values can be OR'd together 
// * 								(p20 doc ecma pour les autres  http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-363%204th%20Edition.pdf)
// * META DATA PADDING	variable size field <=> Zero to three bytes are inserted to maintain 32-bit alignment for the start of the next block. The value of any padding bytes is 0x00.
// */
//
//

//
///*-----------------------------
//*Resource Modifier Chain Block 
//*-----------------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF14 );					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
//fluxPourEcrireEnBinaire.WriteU32(160);							//4 octets, U32 : Data Size: 160 bytes
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
//fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of the modifier chain : 12 bytes 
//fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Modifier Chain Type: 1 - Resource Modifier Chain
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Modifier Chain Attributes : 0
//fluxPourEcrireEnBinaire.WriteU16(0);							//2 octets, U16 : Padding or 32-bit alignment
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Modifier Count : 1 
//
//
//
///*--------------------
//*Max Mesh Description 
//*--------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0x00000001);					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
//fluxPourEcrireEnBinaire.WriteU32(12);							//4 octets, U32 : Face Count: 12
//fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Positon Count : 8
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Count : 0 
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Diffuse Color Count: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Specular Color Count: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Coord Count: 0
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Shading Count: 1
//
///*-------------------
//*Shading Description 
//*-------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0x00000000);					//4 octets, U32 : Shader doesn't use diffuse or specular colors
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Layer Count: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Original Shading ID: 0
//
///*----------------
//*CLOD Description 
//*----------------*/
//fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Minimum Resolution: 8
//fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Final Maximum Resolution: 8
//
///*--------------------
//*Resource Description 
//*--------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position Quality Factor : 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Quality Factor : 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Coord Quality Factor : 0
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Position Inverse Quant : 1.0
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Normal Inverse Quant : 1.0
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Texture Coord Inverse Quant : 1.0
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Diffuse Color Inverse Quant : 1.0
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Specular Inverse Quant : 1.0
//fluxPourEcrireEnBinaire.WriteF32(0.0f);							//4 octets, U32 : Normal Crease Parameter : 0.0
//fluxPourEcrireEnBinaire.WriteF32(0.0f);							//4 octets, U32 : Normal Update Parameter : 0.0
//fluxPourEcrireEnBinaire.WriteF32(0.0f);							//4 octets, U32 : Normal Tolerance Parameter : 0.0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Bone Count : 0
//fluxPourEcrireEnBinaire.WriteU16(0);							//2 octets, U16 : Padding for 32-bit alignment
//
///*----------------------
//*CLOD Mesh Continuation 
//*----------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF3B);					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
//fluxPourEcrireEnBinaire.WriteU32(330);							//4 octets, U32 : Data Size: 330 bytes
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
//fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of Mesh Declaration : 12 bytes
//fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Chain Index : 0
//fluxPourEcrireEnBinaire.WriteU32(12);							//4 octets, U32 : Base Face Count: 12
//fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Base Positon Count : 8
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Normal Count : 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Diffuse Color Count : 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Specular Color Count : 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Texture Coord Count : 0
//
///*--------------
//*Positons Array 
//*--------------*/
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
//fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
//
///*----------
//*Face Array 
//*----------*/
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
//fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 2 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
//fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
//fluxPourEcrireEnBinaire.WriteU32(4);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 3 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
//fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 4 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
//fluxPourEcrireEnBinaire.WriteU32(6);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 5 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 6 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
//fluxPourEcrireEnBinaire.WriteU32(5);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 7 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
//fluxPourEcrireEnBinaire.WriteU32(4);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
//fluxPourEcrireEnBinaire.WriteU32(5);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 8 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(4);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
//fluxPourEcrireEnBinaire.WriteU32(5);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
//fluxPourEcrireEnBinaire.WriteU32(7);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 9 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(4);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
//fluxPourEcrireEnBinaire.WriteU32(6);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
//fluxPourEcrireEnBinaire.WriteU32(7);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 10 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
//fluxPourEcrireEnBinaire.WriteU32(4);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
//fluxPourEcrireEnBinaire.WriteU32(6);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 11 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
//fluxPourEcrireEnBinaire.WriteU32(5);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
//fluxPourEcrireEnBinaire.WriteU32(6);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
//
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 12 Original Shading Index: 0
//fluxPourEcrireEnBinaire.WriteU32(5);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
//fluxPourEcrireEnBinaire.WriteU32(6);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
//fluxPourEcrireEnBinaire.WriteU32(7);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
//fluxPourEcrireEnBinaire.WriteU16(0);							//2 octets, U16 : Padding for 32-bit alignment
//








			





