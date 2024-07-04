package org.zkpk.cs.shiro.utils;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

/**
 * 
 * @author Administrator
 *
 */
public class ReByteSource implements ByteSource, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7009510173159345642L;
	private volatile byte[] bytes;
	private String cachedHex;
	private String cachedBase64;

	public ReByteSource() {}

	public ReByteSource(String string) {
		this.bytes = CodecSupport.toBytes(string);
	}
	
	public ReByteSource(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public String toHex() {
		if ( this.cachedHex == null ) {
			this.cachedHex = Hex.encodeToString(getBytes());
		}
		return this.cachedHex;
	}

	public String toBase64() {
		if ( this.cachedBase64 == null ) {
			this.cachedBase64 = Base64.encodeToString(getBytes());
		}
		return this.cachedBase64;
	}

	public boolean isEmpty() {
		return this.bytes == null || this.bytes.length == 0;
	}
	
	@Override
	public String toString() {
		return toBase64();
	}

	@Override
	public int hashCode() {
		if (this.bytes == null || this.bytes.length == 0) {
			return 0;
		}
		return Arrays.hashCode(this.bytes);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof ByteSource) {
			ByteSource bs = (ByteSource) o;
			return Arrays.equals(getBytes(), bs.getBytes());
		}
		return false;
	}
	
	/**
	 * Returns a new {@code ByteSource} instance representing the specified string's bytes.  The byte
	 * array is obtained assuming {@code UTF-8} encoding.
	 *
	 * @param string the string to represent as a {@code ByteSource} instance.
	 * @return a new {@code ByteSource} instance representing the specified string's bytes.
	 */
	public static ByteSource ofbyte(String string) {
		return new ReByteSource(string);
	}
	
	/**
	 * Returns a new {@code ByteSource} instance representing the specified string's bytes.  The byte
	 * array is obtained assuming {@code UTF-8} encoding.
	 *
	 * @param bytes
	 * @return a new {@code ByteSource} instance representing the specified string's bytes.
	 */
	public static ByteSource ofbyte(byte[] bytes) {
		return new ReByteSource(bytes);
	}
}

