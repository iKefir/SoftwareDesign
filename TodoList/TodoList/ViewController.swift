//
//  ViewController.swift
//  TodoList
//
//  Created by Данил Шкарупин on 24/11/2018.
//  Copyright © 2018 Данил Шкарупин. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.reloadData()
    }
    
    @IBAction func addButtonAction(_ sender: UIBarButtonItem) {
        let alert = UIAlertController(title: "", message: "Give name to list", preferredStyle: .alert)
        alert.addTextField(configurationHandler: { (textField) in
            textField.placeholder = "List name"
        })
        alert.addAction(UIAlertAction(title: "Add", style: .default, handler: { (updateAction) in
            if !(alert.textFields!.first!.text! == "" || alert.textFields!.first!.text! == "SECT KEY" || DBManager.shared.sections.contains(alert.textFields!.first!.text!)) {
                DBManager.shared.createSection(alert.textFields!.first!.text!)
                self.tableView.reloadData()
            }
        }))
        alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
        self.present(alert, animated: false)
    }
}

extension ViewController {
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return DBManager.shared.getTasks(in: section).count + 1
    }
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return DBManager.shared.sections.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tasks = DBManager.shared.getTasks(in: indexPath.section)
        if (indexPath.row == tasks.count) {
            let cell = UITableViewCell()
            cell.textLabel?.text = "+"
            cell.textLabel?.textAlignment = .center
            cell.textLabel?.textColor = .blue
            cell.selectionStyle = .none
            return cell
        }
        let cell = UITableViewCell()
        let attributedText = NSMutableAttributedString(string: tasks[indexPath.row].task)
        if (tasks[indexPath.row].isDone) {
            attributedText.addAttribute(NSAttributedString.Key.strikethroughStyle, value: 2, range: NSMakeRange(0, attributedText.length))
        }
        cell.textLabel?.attributedText = attributedText
        cell.selectionStyle = .none
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if (indexPath.row == DBManager.shared.getTasks(in: indexPath.section).count) {
            let alert = UIAlertController(title: "", message: "Which task to add?", preferredStyle: .alert)
            alert.addTextField(configurationHandler: { (textField) in
                textField.placeholder = "new task"
            })
            alert.addAction(UIAlertAction(title: "Add", style: .default, handler: { (updateAction) in
                DBManager.shared.addTask(in: indexPath.section, value: alert.textFields!.first!.text!)
                self.tableView.reloadData()
            }))
            alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
            self.present(alert, animated: false)
        }
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return DBManager.shared.sections[section]
    }
    
    override func tableView(_ tableView: UITableView, leadingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let tasks = DBManager.shared.getTasks(in: indexPath.section)
        if (indexPath.row != tasks.count) {
            let doneAction = UIContextualAction(style: .normal, title: (tasks[indexPath.row].isDone ? "Undo" : "Done")) { (_, _, _) in
                DBManager.shared.changeIsDoneTask(in: indexPath.section, row: indexPath.row)
                self.tableView.reloadData()
            }
            doneAction.backgroundColor = .blue
            return UISwipeActionsConfiguration(actions: [doneAction])
        }
        return nil
    }
    
    override func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let tasks = DBManager.shared.getTasks(in: indexPath.section)
        let deleteTitle = (indexPath.row == tasks.count ? "Delete list" : "Delete")
        
        let deleteAction = UIContextualAction(style: .destructive, title: deleteTitle) { (_, _, _) in
            if (indexPath.row == tasks.count){
                let alert = UIAlertController(title: "", message: "Do you really want to delete list " + DBManager.shared.sections[indexPath.section], preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Yes", style: .default, handler: { (updateAction) in
                    DBManager.shared.removeSection(indexPath.section)
                    self.tableView.reloadData()
                }))
                alert.addAction(UIAlertAction(title: "No", style: .cancel, handler: {_ in self.tableView.reloadData()}))
                self.present(alert, animated: false)
            } else {
                DBManager.shared.removeTask(in: indexPath.section, row: indexPath.row)
                self.tableView.reloadData()
            }
        }
        
        var actList = [deleteAction]
        
        if (indexPath.row != tasks.count) {
            let editAction = UIContextualAction(style: .normal, title: "Edit") { (_, _, _) in
                let alert = UIAlertController(title: "", message: "Edit list item", preferredStyle: .alert)
                alert.addTextField(configurationHandler: { (textField) in
                    textField.text = tasks[indexPath.row].task
                })
                alert.addAction(UIAlertAction(title: "Update", style: .default, handler: { (updateAction) in
                    DBManager.shared.editTask(in: indexPath.section, row: indexPath.row, value: alert.textFields!.first!.text!)
                    self.tableView.reloadData()
                }))
                alert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: {_ in self.tableView.reloadData()}))
                self.present(alert, animated: false)
            }
            
            actList.append(editAction)
        }
        
        return UISwipeActionsConfiguration(actions: actList)
    }
}
