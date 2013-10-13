BulletShot Stage File Specification
===================================
ビッグエンディアン
フォーマット
------------
以下のフォーマットとする。

	Bytes	Type	Field
	4				Common Header Field (0xEE,0xEA,0xEA,0xDE)
	10		String	BulletShot Header Field ("BulletShot") (42 75 6C 6C 65 74 53 68 6F 74)
	2		Unsigned	Version *1
	1		Unsigned	File Type *2
	1		Byte	Stage Width (X)
	1		Byte	Stage Height (Z)
			String	

*1: 0x0001 : M3 (0.3.02)  
*2: 0x00 : Stage