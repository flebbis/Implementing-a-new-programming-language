/**
 * This module works as the communication bridge between TypeScript LSP and Java type checker
 * 
 *  This module handles spawning the Java type checker as a child process and
 * parsing its JSON output into TypeScript friendly error objects
 * 
 * The Java type checker is built as a standalone JAR that accepts source code
 * as a command-line argument and returns type errors in JSON format
 */
import { spawn } from 'child_process';
import * as path from 'path';
import * as fs from 'fs';
import { URI } from 'vscode-uri';

/**
 * Represents a type error returned from the Java type checker
 * 
 * This interface matches the JSON structure that the Java TypeCheckerServer outputs
 * All line/column numbers are 1-based from Java and will be converted to 0-based
 * for LSP compatibility in the server.ts file
 */
export interface TypeError {
  message: string;
  line: number;
  column: number;
  endLine: number;
  endColumn: number;
  severity: 'error' | 'warning';
}

/**
 * Spawns the Java type checker process to validate source code
 * 
 * @param source - The complete source code string to type check
 * @returns Promise that resolves to an array of TypeError objects
 * @throws Error if the JAR file cannot be found, JAVA process fails, or JSON parsing fails
 */
export async function checkTypes(uri: string, source: string): Promise<TypeError[]> {
  return new Promise((resolve, reject) => {
    
        const jarPath = path.join(__dirname, '../../LLVMINI-1.0-SNAPSHOT.jar');
    
    
    //const java = spawn("java", ["-jar", jarPath, URI.parse(uri).fsPath, document.getText()]);
    console.error("SOURCE " + source + " " + uri)
        const java = spawn('java', ['-jar', jarPath, URI.parse(uri).fsPath, source]);
    
    let stdout = '';
    let stderr = '';
    
    /**
     * Handle stdout data chunks
     * The Java process writes JSON to stdout which may arrive in multiple chunks
     */
    java.stdout.on('data', (data) => {
      stdout += data.toString();
      console.error(`[TypeChecker] stdout: ${data.toString().substring(0, 200)}`);
    });
    
    /**
     * Handle stderr data chunks
     * This captures any error output from the Java process (e.g., exceptions)
     */
    java.stderr.on('data', (data) => {
      stderr += data.toString();
      console.error(`[TypeChecker] stderr: ${data.toString()}`);
    });
    
    /**
     * Process completion handler
     * Called when the Java process ecits, either successfully or with an error
     */
    java.on('close', (code) => {
      console.error(`[TypeChecker] Process exited with code: ${code}`);
      console.error(`[TypeChecker] Full stdout: ${stdout}`);
      console.error(`[TypeChecker] Full stderr: ${stderr}`);
      
      // Non-zero exit code indicates the Java process failed
      if (code !== 0) {
        reject(new Error(stderr || `Java process exited with code ${code}`));
        return;
      }
      
      // Check for empty response (should never happen for valid Java output)
      if (!stdout || stdout.trim() === '') {
        console.error('[TypeChecker] Empty stdout!');
        reject(new Error('Java returned empty response'));
        return;
      }
      
      /**
       * Parse the JSON response from Java
       * Expected format - arrsay of TypeError objects
       */
      try {
        const errors = JSON.parse(stdout).typeErrors;
        console.error(`[TypeChecker] Parsed ${errors.length} errors`);
        resolve(errors);
      } catch (err) {
        const error = err as Error;
        console.error(`[TypeChecker] Failed to parse JSON: ${stdout}`);
        reject(new Error(`Failed to parse JSON from Java: ${error.message}`));
      }
    });
    
    /**
     * Handle process spawn errors
     * This catches issues like 'java' command not found in PATH
     */
    java.on('error', (error) => {
      console.error(`[TypeChecker] Spawn error: ${error.message}`);
      reject(new Error('Failed to start Java process: ' + error.message));
    });
  });
}