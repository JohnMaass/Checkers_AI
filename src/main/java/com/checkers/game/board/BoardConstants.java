package com.checkers.game.board;

/**
 * The BoardConstants class contains constants used to convert between different
 * representations.
 * 
 * @author maass
 */
public class BoardConstants {
	public static int[][] boardNotation={
			{0,1,0,2,0,3,0,4},
			{5,0,6,0,7,0,8,0},
			{0,9,0,10,0,11,0,12},
			{13,0,14,0,15,0,16,0},
			{0,17,0,18,0,19,0,20},
			{21,0,22,0,23,0,24,0},
			{0,25,0,26,0,27,0,28},
			{29,0,30,0,31,0,32,0}};
	
	public static int[][] boardNotationInversion={
			{0,32,0,31,0,30,0,29},
			{28,0,27,0,26,0,25,0},
			{0,24,0,23,0,22,0,21},
			{20,0,19,0,18,0,17,0},
			{0,16,0,15,0,14,0,13},
			{12,0,11,0,10,0,9,0},
			{0,8,0,7,0,6,0,5},
			{4,0,3,0,2,0,1,0}};
	
	public static int[] convertNotationBack={
			0,
			32,
			31,
			30,
			29,
			28,
			27,
			26,
			25,
			24,
			23,
			22,
			21,
			20,
			19,
			18,
			17,
			16,
			15,
			14,
			13,
			12,
			11,
			10,
			9,
			8,
			7,
			6,
			5,
			4,
			3,
			2,
			1
	};
}
