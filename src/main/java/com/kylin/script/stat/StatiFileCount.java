package com.kylin.script.stat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kylin.script.util.FileClassUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class StatiFileCount {

	public static void main(String[] args) throws IOException {
		group();
		//organization();
	}

	public static void group() throws IOException {
		// 群组
		Map<String, Group> groupMap = FileClassUtils.fileToMap(new File("data/stat/group_list.txt"),
				(String[] items) -> {
					return new Group(items[0], items[1]);
				});

		Map<String, GroupFileCount> groupFileCountMap = FileClassUtils
				.fileToMap(new File("data/stat/group_file_list.txt"), (String[] items) -> {
					return new GroupFileCount(items[0], items[1]);
				});

		//List<String> groupLineResult = new ArrayList<>();
		
		List<StatCount> groupStatCount = new ArrayList<>();

		for (Entry<String, GroupFileCount> entry : groupFileCountMap.entrySet()) {
			// System.out.println(entry.getKey());
			// System.out.println(entry.getKey().length());
			Group group = groupMap.get(entry.getKey());
			if (group != null) {
				// System.out.println("grou->" + group);

				// System.out.println(group.getGroupId().length());
				//groupLineResult.add(group.getGroupName() + "\t" + entry.getValue().getFileCount());
				groupStatCount.add(new StatCount(group.getGroupName(), Integer.parseInt(entry.getValue().getFileCount())));
			}
		}

		groupStatCount.sort((s1, s2) -> s2.getCount() - s1.getCount());
		groupStatCount.stream().forEach(System.out::println);
	}

	public static void organization() throws IOException {
		// 组织
		Map<String, Organization> organizationMap = FileClassUtils
				.fileToMap(new File("data/stat/organization_list.txt"), (String[] items) -> {
					return new Organization(items[0], items[1]);
				});

		Map<String, OrganizationFileCount> organizationFileCountMap = FileClassUtils
				.fileToMap(new File("data/stat/organization_file_list.txt"), (String[] items) -> {
					return new OrganizationFileCount(items[0], items[1]);
				});

		List<StatCount> organizationResult = new ArrayList<>();

		for (Entry<String, OrganizationFileCount> entry : organizationFileCountMap.entrySet()) {
			Organization organization = organizationMap.get(entry.getKey());
			if (organization != null) {
				organizationResult.add(new StatCount(organization.getOrganizationName(), Integer.parseInt(entry.getValue().getFileCount())));
			}
		}
		organizationResult.sort((s1, s2) -> s2.getCount() - s1.getCount());
		organizationResult.stream().forEach(System.out::println);
		
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class Group {
		String groupId;
		String groupName;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class GroupFileCount {
		String groupId;
		String fileCount;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class Organization {
		String organizationId;
		String organizationName;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class OrganizationFileCount {
		String organizationId;
		String fileCount;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	static class StatCount {
		String name;
		Integer count;
		@Override
		public String toString() {
			return name + "\t" + count;
		}
	}
}
