//
//  DBManager.swift
//  TodoList
//
//  Created by Данил Шкарупин on 24/11/2018.
//  Copyright © 2018 Данил Шкарупин. All rights reserved.
//

import Foundation
import UIKit

class Task : Codable {
    var task: String
    var isDone: Bool
    
    init(_ taskGot: String, _ isDoneGot: Bool) {
        task = taskGot
        isDone = isDoneGot
    }
}

class DBManager: NSObject {
    
    static let shared = DBManager()
    
    var sections: [String] {
        set(newData) {
            UserDefaults.standard.set(newData, forKey: SECT_KEY)
        }
        get {
            return getSections()
        }
    }
    
    private let lock = NSLock()
    private let SECT_KEY = "SECT KEY"
    
    func initList() {
        UserDefaults.standard.set([], forKey: SECT_KEY)
        createSection("Unsorted")
    }
    
    func getSections() -> [String] {
        if let arr = UserDefaults.standard.array(forKey: SECT_KEY) as? [String] {
            return arr
        } else {
            initList()
            let arr = UserDefaults.standard.array(forKey: SECT_KEY) as! [String]
            return arr
        }
    }
    
    func createSection(_ name: String) {
        sections.append(name)
        UserDefaults.standard.set(try! PropertyListEncoder().encode(Array<Task>.init()), forKey: name)
    }
    
    func removeSection(_ row: Int) {
        let name = sections[row]
        sections.remove(at: row)
        UserDefaults.standard.removeObject(forKey: name)
    }
    
    private func setTasks(in section: Int, value: [Task]) {
        UserDefaults.standard.set(try! PropertyListEncoder().encode(value), forKey: sections[section])
    }
    
    func getTasks(in section: Int) -> [Task] {
        let data = UserDefaults.standard.value(forKey: sections[section]) as! Data
        let arr = try! PropertyListDecoder().decode(Array<Task>.self, from: data)
        return arr
    }
    
    func addTask(in section: Int, value: String) {
        var arr = getTasks(in: section)
        arr.append(Task(value, false))
        setTasks(in: section, value: arr)
    }
    
    func editTask(in section: Int, row: Int, value: String) {
        var arr = getTasks(in: section)
        arr[row].task = value
        setTasks(in: section, value: arr)
    }
    
    func removeTask(in section: Int, row: Int) {
        var arr = getTasks(in: section)
        arr.remove(at: row)
        setTasks(in: section, value: arr)
    }
    
    func changeIsDoneTask(in section: Int, row: Int) {
        var arr = getTasks(in: section)
        arr[row].isDone = !arr[row].isDone
        setTasks(in: section, value: arr)
    }
}
