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
		// TODO trouver et comprendre comment calculer les 2 variables
//		int tailleDeclaration = 0;
//		int tailleSuiteDeclaration = 0;

		File fichierDeSortieU3D = new File("c:/TMP/unFondBleu.u3d");

		try
		{
			BitStreamWrite fluxPourEcrireEnBinaire = new BitStreamWrite();
			/*
			 * File Header Block ----> Declaration Block ----> Continuation Block
			 * 							/|\			/|\			/|\			/|\
			 * 							 |___________|			 |___________|
			 * 
			 * File  Header  Block	 contain information about the file. The loader uses the File Header Block to determine how to read the file.
			 * Declaration Blocks	 contain information about the objects in the file. All objects must be defined in a Declaration Block. The File Header Block is considered to be a Declaration Block.
			 * Continuation  Blocks  can  provide  additional  information  for  objects  declared  in  a Declaration Block. Each Continuation Block must be associated with a Declaration Block.
			 * 
			 * File Header is the only required block in a file. It contains information about the rest of the file. The File Header block is considered a declaration block. 
			 * The Priority Update Block is the continuation block type associated with the File Header
			 * File Header (blocktype: 0x00443355)
			 *    |
			 *   \|/ 
			 * Major Version			I16 <=> The  current  major version  number  is  zero
			 *   \|/
			 * Minor Version			I16 <=> Minor  Version  is  implementation  dependent  and  may  contain  any  value
			 *   \|/
			 * (Profile Identifier)		U32 <=> Used to identify optional features used by this file. Valid values are
			 * 	  |								0x00000000 – Base profile; no optional features used
			 * 	  |								0x00000002 – Extensible profile; uses extensibility features
			 * 	  |								0x00000004 – No compression mode
			 * 	 \|/							0x00000008 – Defined units (p23 doc ECMA pour plus d'info  http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-363%204th%20Edition.pdf)
			 * Declaration Size			U32 <=> number of bytes in the Declaration Block section of the file (Declaration Size includes the size of the File Header block and all declaration blocks including any padding bytes in those blocks)
			 * 	 \|/
			 * File Size				U64 <=> number of bytes in this file (File Size includes the size of all blocks including the File Header block and any padding bytes in those blocks. File Size does not include the size of any external files referenced by the contents of any block)
			 * 	 \|/
			 * Charactere Encoding		U32 <=> the  encoding  used  for  strings  in  this  file. For the current version of U3D, the Character Encoding shall be UTF-8 corresponds to a MIB enum value of 106
			 * 	 \|/
			 * (Units Scaling Factor)	F64 <=> shall be present only if the defined units bit in the Profile Identifier is set. Units Scaling Factor defines the units used in this file.
			 */
			fluxPourEcrireEnBinaire.WriteU32(0x00443355);						//4 octets, U32 : BlockType
			fluxPourEcrireEnBinaire.WriteU32(60);								//4 octets, U32 : DataSize
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : MetaDataSize
			fluxPourEcrireEnBinaire.WriteI16((short)0);							//2 octets, I16 : MAJOR VERSION. Cette valeur doit être 0 selon la spécification officielle.
			fluxPourEcrireEnBinaire.WriteI16((short)0);							//2 octets, I16 : MINOR VERSION. Il peut s'agir de n'importe quelle valeur, mais pour des raisons de santé mentale, nous utiliserons la valeur 0.
			fluxPourEcrireEnBinaire.WriteU32(0x00000000);						//4 octets, U32 : L'identifiant du profil. Il est utilisé pour identifier des caractéristiques optionnelles dans le fichier.
			fluxPourEcrireEnBinaire.WriteU32(297);								//4 octets, U32 : Declaration Size: 297 bytes
			fluxPourEcrireEnBinaire.WriteU64(639);								//8 octets, U64 : File Size: 639 bytes
			fluxPourEcrireEnBinaire.WriteU32(106);								//4 octets, U32 : Le codage de toutes les chaînes de caractères du fichier.
			
			
			
			
			/*--------------------------------
			 *Node Modifier Chain Block 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF14);						//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(68);								//4 octets, U32 : Data Size: 68 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);	    						//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(8);								//2 octets, U16 : Size of the Name of the Modifier Chain: 8 bytes
			fluxPourEcrireEnBinaire.WriteString("MeshNode");					// STRING bytes : Name of the Modifier Chain: MeshNode
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Type: 0. Node Modifier Chain
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Attributes
//			fluxPourEcrireEnBinaire.WriteU16(0);								//2 octets, U16 : Padding or 32-bit alignment
			fluxPourEcrireEnBinaire.WriteU32(1);								//4 octets, U32 : Modifier Count: 1
			
//			
//			
//			
//			
//			
//			
			
			// Ecriture blockType & données
			DataBlock monPremierBloc = fluxPourEcrireEnBinaire.GetDataBlock();
			long dataSizeHeaderBlock = monPremierBloc.getDataSize()-12; // on soustrait les 12 octets du HeaderBlock (BlockType/DataSize/MetaDataSize)
			System.out.println("Ligne61 :: DataSize = " + dataSizeHeaderBlock); 
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
///*-----------------------
//*Model Node Block 
//*-----------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF22 );					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
//fluxPourEcrireEnBinaire.WriteU32(32);							//4 octets, U32 : Data Size: 32 bytes
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
//fluxPourEcrireEnBinaire.WriteU16(8);							//2 octets, U16 : Size of the model node name : 8 bytes 
//fluxPourEcrireEnBinaire.WriteString("MeshNode");				// STRING bytes : Model node name : MeshNode
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Parent node count : 0
//fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of the resource modifier chain : 12 bytes
//fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the resource modifier chain: MeshResource
//fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Visibility : 3 - Both front and back visible
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
///*---------------------
//*CLOD Mesh Declaration 
//*---------------------*/
//fluxPourEcrireEnBinaire.WriteU32(0xFFFFFF31);					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
//fluxPourEcrireEnBinaire.WriteU32(142);							//4 octets, U32 : Data Size: 142 bytes
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
//fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of Mesh Declaration : 12 bytes 
//fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
//fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Chain Index : 0
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








			





