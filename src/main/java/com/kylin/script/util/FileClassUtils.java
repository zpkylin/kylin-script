package com.kylin.script.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class FileClassUtils {

	public static <E> List<E> fileToList(File file, BusinessHandler<E> businessHandler) throws IOException {
		List<E> result = new ArrayList<E>();
		for (String line : FileUtils.readLines(file)) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			String[] items = StringUtils.split(line, "\t");
			result.add(businessHandler.handle(items));
		}
		return result;
	}

	public static <E> Map<String, E> fileToMap(File file, BusinessHandler<E> businessHandler) throws IOException {
		Map<String, E> result = new TreeMap<String, E>();
		for (String line : FileUtils.readLines(file)) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			String[] items = StringUtils.split(line, "\t");
			result.put(items[0], businessHandler.handle(items));
		}
		return result;
	}

}
