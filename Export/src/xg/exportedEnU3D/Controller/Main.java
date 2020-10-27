package xg.exportedEnU3D.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import xg.exportedEnU3D.Model.BitStreamWrite;
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
			FileOutputStream fluxDeSortiePourEcrireLeFichierU3D = new FileOutputStream(fichierDeSortieU3D);
			BitStreamWrite fluxPourEcrireEnBinaire = new BitStreamWrite();

			/*
			 * File Header Block
			 * premier bloc du fichier U3D : contient des informations décrivant 
			 * la taille totale du fichier, 
			 * la taille totale des blocs de déclaration, 
			 * le codage des caractères de toutes les chaînes du fichier
			 */
			//fluxPourEcrireEnBinaire.WriteU32(TYPE_FILE_HEADER);					//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier. Cette valeur est 0x00443355 pour le bloc de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(32);								//4 octets, U32 : Data Size: 32 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);	    						//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteI16(EXPORTER_VERSION_MAJOR);			//2 octets, I16 : La version principale du fichier. Cette valeur doit être 0 selon la spécification officielle.
			fluxPourEcrireEnBinaire.WriteI16(EXPORTER_VERSION_MINOR);			//2 octets, I16 : La version mineure du fichier. Il peut s'agir de n'importe quelle valeur, mais pour des raisons de santé mentale, nous utiliserons la valeur 0.
			fluxPourEcrireEnBinaire.WriteU32(PROFILE_IDENTIFIER);				//4 octets, U32 : L'identifiant du profil. Il est utilisé pour identifier des caractéristiques optionnelles dans le fichier.
			/*
			 * Les options sont les suivantes :
			 * 0x0000000C : Aucun mode de compression et les unités sont définies
			 * 0x00000000 : Aucune caractéristique optionnelle utilisée
			 * 0x00000002 : Utilise les fonctions d'extensibilité. Cela indique que le fichier peut utiliser des blocs de type "New Object Type".
			 * 0x00000004 : Pas de mode de compression. Cela signifie que le fichier ne contient aucune valeur compressée.
			 * 0x00000008 : Unités définies. Ceci indique que les objets du fichier sont définis avec des unités.
			 */
			fluxPourEcrireEnBinaire.WriteU32(297);								//4 octets, U32 : Declaration Size: 297 bytes
			fluxPourEcrireEnBinaire.WriteU64(639);								//8 octets, U64 : File Size: 639 bytes
			fluxPourEcrireEnBinaire.WriteU32(CHARACTER_ENCODING);				//4 octets, U32 : Le codage de toutes les chaînes de caractères du fichier. Cette valeur est la valeur de l'énumération des MIB qui peut être trouvée sur http://www.iana.org/assignments/character-sets
			
			/*--------------------------------
			 *Node Modifier Chain Block 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(TYPE_MOFIFIER_CHAIN);				//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(68);								//4 octets, U32 : Data Size: 68 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);	    						//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(8);								//2 octets, U16 : Size of the Name of the Modifier Chain: 8 bytes
			fluxPourEcrireEnBinaire.WriteString("MeshNode");					// STRING bytes : Name of the Modifier Chain: MeshNode
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Type: 0. Node Modifier Chain
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Attributes
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Padding or 32-bit alignment
			fluxPourEcrireEnBinaire.WriteU32(1);								//4 octets, U32 : Modifier Count: 1//Ajout en-tête au bloc
			
			/*--------------------------------
			 *Node Modifier Chain Block 
			 *--------------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(TYPE_MOFIFIER_CHAIN);				//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(68);								//4 octets, U32 : Data Size: 68 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);	    						//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(8);								//2 octets, U16 : Size of the Name of the Modifier Chain: 8 bytes
			fluxPourEcrireEnBinaire.WriteString("MeshNode");					// STRING bytes : Name of the Modifier Chain: MeshNode
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Type: 0. Node Modifier Chain
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Modifier Chain Attributes
			fluxPourEcrireEnBinaire.WriteU32(0);								//4 octets, U32 : Padding or 32-bit alignment
			fluxPourEcrireEnBinaire.WriteU32(1);								//4 octets, U32 : Modifier Count: 1//Ajout en-tête au bloc
			
			/*-----------------------
			 *Model Node Block 
			 *-----------------------*/
			fluxPourEcrireEnBinaire.WriteU32(TYPE_MODEL_NODE);				//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(32);							//4 octets, U32 : Data Size: 32 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(8);							//2 octets, U16 : Size of the model node name : 8 bytes 
			fluxPourEcrireEnBinaire.WriteString("MeshNode");				// STRING bytes : Model node name : MeshNode
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Parent node count : 0
			fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of the resource modifier chain : 12 bytes
			fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the resource modifier chain: MeshResource
			fluxPourEcrireEnBinaire.WriteU32(3);							//4 octets, U32 : Visibility : 3 - Both front and back visible
			
			/*-----------------------------
			 *Resource Modifier Chain Block 
			 *-----------------------------*/
			fluxPourEcrireEnBinaire.WriteU32(TYPE_MOFIFIER_CHAIN);			//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(160);							//4 octets, U32 : Data Size: 160 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of the modifier chain : 12 bytes 
			fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Modifier Chain Type: 1 - Resource Modifier Chain
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Modifier Chain Attributes : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Padding or 32-bit alignment
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Modifier Count : 1 
			
			/*---------------------
			 *CLOD Mesh Declaration 
			 *---------------------*/
			fluxPourEcrireEnBinaire.WriteU32(CLOD_MESH_DECLARATION);		//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(142);							//4 octets, U32 : Data Size: 142 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of Mesh Declaration : 12 bytes 
			fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Chain Index : 0
			
			/*--------------------
			 *Max Mesh Description 
			 *--------------------*/
			fluxPourEcrireEnBinaire.WriteU32(MAX_MESH_DECLARATION);			//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(12);							//4 octets, U32 : Face Count: 12
			fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Positon Count : 8
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Count : 0 
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Diffuse Color Count: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Specular Color Count: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Coord Count: 0
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Shading Count: 1
			
			/*-------------------
			 *Shading Description 
			 *-------------------*/
			fluxPourEcrireEnBinaire.WriteU32(PROFILE_IDENTIFIER);			//4 octets, U32 : Shader doesn't use diffuse or specular colors
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Layer Count: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Original Shading ID: 0
			
			/*----------------
			 *CLOD Description 
			 *----------------*/
			fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Minimum Resolution: 8
			
			/*--------------------
			 *Resource Description 
			 *--------------------*/
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position Quality Factor : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Quality Factor : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Texture Coord Quality Factor : 0
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Position Inverse Quant : 1.0
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Normal Inverse Quant : 1.0
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Texture Coord Inverse Quant : 1.0
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Diffuse Color Inverse Quant : 1.0
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, F32 : Specular Inverse Quant : 1.0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Crease Parameter : 0.0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Update Parameter : 0.0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Normal Tolerance Parameter : 0.0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Bone Count : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Padding for 32-bit alignment
			
			/*----------------------
			 *CLOD Mesh Continuation 
			 *----------------------*/
			fluxPourEcrireEnBinaire.WriteU32(CLOD_MESH_CONTINUATION);		//4 octets, U32 : Le type de bloc du verrou de l'en-tête de fichier.
			fluxPourEcrireEnBinaire.WriteU32(330);							//4 octets, U32 : Data Size: 330 bytes
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Metadata Size: 0 bytes
			fluxPourEcrireEnBinaire.WriteU16(12);							//2 octets, U16 : Size of the name of Mesh Declaration : 12 bytes
			fluxPourEcrireEnBinaire.WriteString("MeshResource");			// STRING bytes : Name of the modifier chain: MeshResource
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Chain Index : 0
			fluxPourEcrireEnBinaire.WriteU32(12);							//4 octets, U32 : Base Face Count: 12
			fluxPourEcrireEnBinaire.WriteU32(8);							//4 octets, U32 : Base Positon Count : 8
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Normal Count : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Diffuse Color Count : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Specular Color Count : 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Base Texture Coord Count : 0
			
			/*--------------
			 *Positons Array 
			 *--------------*/
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 1: ( 0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 2: ( 1.0, 0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 3: ( 0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 4: ( 0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 5: ( 1.0, 1.0, 0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 6: ( 1.0, 0, 1.0 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 7: ( 0, 1.0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
			fluxPourEcrireEnBinaire.WriteF32(1.0f);							//4 octets, U32 : Position 8: ( 1.0, 1.0, 1.0 )
			
			/*----------
			 *Face Array 
			 *----------*/
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 2 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 2 Position Indices ( 1, 2, 4 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 3 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 3 Position Indices ( 0, 2, 3 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 4 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 4 Position Indices ( 2, 3, 6 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 5 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 5 Position Indices ( 0, 1, 3 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 6 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 6 Position Indices ( 1, 3, 5 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 7 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 7 Position Indices ( 1, 4, 5 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 8 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 8 Position Indices ( 4, 5, 7 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 9 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 9 Position Indices ( 4, 6, 7 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 10 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 10 Position Indices ( 2, 4, 6 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 1 Position Indices ( 0, 1, 2 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 11 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 11 Position Indices ( 3, 5, 6 )
			
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 12 Original Shading Index: 0
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
			fluxPourEcrireEnBinaire.WriteU32(1);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
			fluxPourEcrireEnBinaire.WriteU32(2);							//4 octets, U32 : Face 12 Position Indices ( 5, 6, 7 )
			fluxPourEcrireEnBinaire.WriteU32(0);							//4 octets, U32 : Pad
			
			DataBlock dataBlockContientToutesLesDonnees = fluxPourEcrireEnBinaire.GetDataBlock();
			dataBlockContientToutesLesDonnees.setBlockType(TYPE_FILE_HEADER);
			
			
			
			
			
			
			// Ecriture blockType & données
			WritableByteChannel canalDEcritureEnBinaire = Channels.newChannel(fluxDeSortiePourEcrireLeFichierU3D);
			WriterU3D.writeDataBlock(dataBlockContientToutesLesDonnees, canalDEcritureEnBinaire);

			//FIN
			fluxDeSortiePourEcrireLeFichierU3D.close();
			System.out.println("Travail effectué avec succès : " + fichierDeSortieU3D);
//38.09
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}