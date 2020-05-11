/*
 * Copyright 2018 - 2020 Valtech GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.valtech.aecu.api.groovy.console.bindings;

import de.valtech.aecu.api.groovy.console.bindings.filters.FilterBy;
import de.valtech.aecu.api.service.AecuException;

import org.apache.sling.api.resource.PersistenceException;
import org.osgi.annotation.versioning.ProviderType;

import java.util.Map;

/**
 * This class provides the builder methods to perform a content upgrade.
 * 
 * @author Roxana Muresan
 * @author Roland Gruber
 */
@ProviderType
public interface ContentUpgrade {

    /**
     * Loops for given list of resources.
     * 
     * @param paths list of paths
     * @return upgrade object
     **/
    ContentUpgrade forResources(String[] paths);

    /**
     * Loops for all child resources of the given path. The path itself is not included.
     * 
     * @param path path
     * @return upgrade object
     **/
    ContentUpgrade forChildResourcesOf(String path);

    /**
     * Loops recursive for all child resources of the given path. The path itself is not included.
     *
     * @param path path
     * @return upgrade object
     **/
    ContentUpgrade forDescendantResourcesOf(String path);


    /**
     * Loops recursive over all resources contained in the subtree at the given path.
     *
     * @param path path
     * @return upgrade object
     */
    ContentUpgrade forResourcesInSubtree(String path);

    /**
     * Loops over resources found by SQL2 query.
     * 
     * @param query query string
     * @return upgrade object
     */
    ContentUpgrade forResourcesBySql2Query(String query);

    /**
     * Filters by existence of a single property.
     * 
     * @param name property name
     * @return upgrade object
     */
    ContentUpgrade filterByHasProperty(String name);

    /**
     * Filters by a single property.
     * 
     * @param name  property name
     * @param value property value
     * @return upgrade object
     */
    ContentUpgrade filterByProperty(String name, Object value);

    /**
     * Filters by a single property using a regular expression for the value. This is intended for
     * single value properties.
     * 
     * @param name  property name
     * @param regex regular expression to match value
     * @return upgrade object
     */
    ContentUpgrade filterByPropertyRegex(String name, String regex);

    /**
     * Filters by checking if any property matches the given regular expression for the value. This
     * is intended for single value properties.
     * 
     * @param regex regular expression to match value
     * @return upgrade object
     */
    ContentUpgrade filterByAnyPropertyRegex(String regex);

    /**
     * Filters by properties. Can be used also for Multi-value properties.
     * 
     * @param conditionProperties properties to filter
     * @return upgrade object
     **/
    ContentUpgrade filterByProperties(Map<String, Object> conditionProperties);

    /**
     * Filters by multi-value with the given name containing the given conditionValues
     *
     * @param name            name of the multi-value property
     * @param conditionValues values to search for
     * @return upgrade object
     */
    ContentUpgrade filterByMultiValuePropContains(String name, Object[] conditionValues);

    /**
     * Filters by node name exact match.
     * 
     * @param nodeName node name
     * @return upgrade object
     */
    ContentUpgrade filterByNodeName(String nodeName);

    /**
     * Filters by node name using regular expression.
     * 
     * @param regex regular expression (Java standard pattern)
     * @return upgrade object
     */
    ContentUpgrade filterByNodeNameRegex(String regex);

    /**
     * Filters by node path using regular expression.
     *
     * @param regex regular expression (Java standard pattern)
     * @return upgrade object
     */
    ContentUpgrade filterByPathRegex(String regex);

    /**
     * Filters by using the given filter.
     * 
     * @param filter filter
     * @return upgrade object
     */
    ContentUpgrade filterWith(FilterBy filter);

    /**
     * Sets a property value.
     *
     * @param name  property name
     * @param value property value
     * @return upgrade object
     **/
    ContentUpgrade doSetProperty(String name, Object value);

    /**
     * Sets a property value regardless of it's previous type.
     *
     * @param name  property name
     * @param value property value
     * @return upgrade object
     **/
    ContentUpgrade doForceProperty(String name, Object value);

    /**
     * Deletes a property if existing.
     * 
     * @param name property name
     * @return upgrade object
     */
    ContentUpgrade doDeleteProperty(String name);

    /**
     * Renames a property if existing.
     * 
     * @param oldName old property name
     * @param newName new property name
     * @return upgrade object
     */
    ContentUpgrade doRenameProperty(String oldName, String newName);

    /**
     * Copies a property to a relative path.
     * 
     * @param name                 property name
     * @param newName              new property name
     * @param relativeResourcePath relative path
     * @return upgrade object
     */
    ContentUpgrade doCopyPropertyToRelativePath(String name, String newName, String relativeResourcePath);

    /**
     * Moves a property to a relative path.
     * 
     * @param name                 property name
     * @param newName              new property name
     * @param relativeResourcePath relative path
     * @return upgrade object
     */
    ContentUpgrade doMovePropertyToRelativePath(String name, String newName, String relativeResourcePath);

    /**
     * Adds values to a multivalue property.
     * 
     * @param name   property name
     * @param values values
     * @return upgrade object
     */
    ContentUpgrade doAddValuesToMultiValueProperty(String name, String[] values);

    /**
     * Removes values of a multivalue property.
     * 
     * @param name   property name
     * @param values values to remove
     * @return upgrade object
     */
    ContentUpgrade doRemoveValuesOfMultiValueProperty(String name, String[] values);

    /**
     * Replaces values in a multivalue property.
     * 
     * @param name      property name
     * @param oldValues values to remove
     * @param newValues values to add
     * @return upgrade object
     */
    ContentUpgrade doReplaceValuesOfMultiValueProperty(String name, String[] oldValues, String[] newValues);

    /**
     * Replaces a substring in all properties of the matching resource. Only applies to String
     * properties.
     * 
     * @param oldValue old value
     * @param newValue new value
     * @return upgrade object
     */
    ContentUpgrade doReplaceValueInAllProperties(String oldValue, String newValue);

    /**
     * Replaces a substring in specific properties of the matching resource. Only applies to String
     * properties.
     * 
     * @param oldValue      old value
     * @param newValue      new value
     * @param propertyNames property names that should be checked
     * @return upgrade object
     */
    ContentUpgrade doReplaceValueInProperties(String oldValue, String newValue, String[] propertyNames);

    /**
     * Replaces a substring in all properties of the matching resource using a regular expression.
     * Only applies to String properties.
     * 
     * @param searchRegex regex to match old value
     * @param replacement new value, may contain matcher groups (e.g. $1)
     * @return upgrade object
     */
    ContentUpgrade doReplaceValueInAllPropertiesRegex(String searchRegex, String replacement);

    /**
     * Replaces a substring in specific properties of the matching resource using a regular
     * expression. Only applies to String properties.
     * 
     * @param searchRegex   regex to match old value
     * @param replacement   new value, may contain matcher groups (e.g. $1)
     * @param propertyNames property names that should be checked
     * @return upgrade object
     */
    ContentUpgrade doReplaceValueInPropertiesRegex(String searchRegex, String replacement, String[] propertyNames);

    /**
     * Renames a resource to the given name.
     * 
     * @param newName path
     * @return newName new name
     */
    ContentUpgrade doRename(String newName);

    /**
     * Copies a resource to a relative path.
     * 
     * @param relativePath path
     * @return upgrade object
     */
    ContentUpgrade doCopyResourceToRelativePath(String relativePath);

    /**
     * Moves a resource to a relative path.
     * 
     * @param relativePath path
     * @return upgrade object
     */
    ContentUpgrade doMoveResourceToRelativePath(String relativePath);

    /**
     * Moves a resource if its path matches the pattern to the path obtained by applying the
     * replacement expression
     *
     * @param matchPattern   regular expression for matching the resource path
     * @param targetPathExpr expression to calculate the target path, can contain matched group
     *                       references $1, $2, ...
     * @return upgrade object
     */
    ContentUpgrade doMoveResourceToPathRegex(String matchPattern, String targetPathExpr);

    /**
     * Deletes the child resources if supplied. If no children are specified it deletes the resource
     * itself.
     * 
     * @return upgrade object
     */
    ContentUpgrade doDeleteResource(String... children);

    /**
     * Creates a new resource under the current one.
     * 
     * @param name        resource name
     * @param primaryType jcr:primaryType
     * @return upgrade object
     */
    ContentUpgrade doCreateResource(String name, String primaryType);

    /**
     * Creates a new resource under the current one.
     * 
     * @param name        resource name
     * @param primaryType jcr:primaryType
     * @param properties  properties excl. jcr:primaryType
     * @return upgrade object
     */
    ContentUpgrade doCreateResource(String name, String primaryType, Map<String, Object> properties);

    /**
     * Creates a new resource under the current one.
     * 
     * @param name         resource name
     * @param primaryType  jcr:primaryType
     * @param relativePath relative path
     * @return upgrade object
     */
    ContentUpgrade doCreateResource(String name, String primaryType, String relativePath);

    /**
     * Creates a new resource under the current one.
     * 
     * @param name         resource name
     * @param primaryType  jcr:primaryType
     * @param properties   properties excl. jcr:primaryType
     * @param relativePath relative path
     * @return upgrade object
     */
    ContentUpgrade doCreateResource(String name, String primaryType, Map<String, Object> properties, String relativePath);

    /**
     * Activates the resource.
     * 
     * @return upgrade object
     */
    ContentUpgrade doActivateResource();

    /**
     * Deactivates the resource.
     * 
     * @return upgrade object
     */
    ContentUpgrade doDeactivateResource();

    /**
     * Performs a custom action with providing a function.
     * 
     * @param action action to perform on resource
     * @return upgrade object
     */
    ContentUpgrade doCustomResourceBasedAction(CustomResourceAction action);

    /**
     * Activates the page where the resource is located.
     * 
     * @return upgrade object
     */
    ContentUpgrade doActivateContainingPage();

    /**
     * Activates the page tree where the resource is located.
     * 
     * @return upgrade object
     */
    ContentUpgrade doTreeActivateContainingPage();

    /**
     * Activates the page tree where the resource is located.
     * 
     * @param skipDeactivated skip pages that are deactivated
     * @return upgrade object
     */
    ContentUpgrade doTreeActivateContainingPage(boolean skipDeactivated);

    /**
     * Deactivates the page where the resource is located.
     * 
     * @return upgrade object
     */
    ContentUpgrade doDeactivateContainingPage();

    /**
     * Deletes the page where the resource is located. This will not work if called multiple times
     * for the same page.
     * 
     * @return upgrade object
     */
    ContentUpgrade doDeleteContainingPage();

    /**
     * Adds tags to the containing page of the matching resource.
     * 
     * @param tags tag IDs or paths
     * @return upgrade object
     */
    ContentUpgrade doAddTagsToContainingPage(String... tags);

    /**
     * Sets tags for the containing page of the matching resource. All existing tags are
     * overwritten.
     * 
     * @param tags tag IDs or paths
     * @return upgrade object
     */
    ContentUpgrade doSetTagsForContainingPage(String... tags);

    /**
     * Removes tags from the containing page of the matching resource.
     * 
     * @param tags tag IDs or paths
     * @return upgrade object
     */
    ContentUpgrade doRemoveTagsFromContainingPage(String... tags);

    /**
     * Checks if the containing page renders with status code 200.
     * 
     * @return upgrade object
     */
    ContentUpgrade doCheckPageRendering();

    /**
     * Checks if the containing page renders with given status code.
     * 
     * @param code status code
     * @return upgrade object
     */
    ContentUpgrade doCheckPageRendering(int code);

    /**
     * Checks if the containing page renders with status code 200 and contains given text.
     * 
     * @param textPresent page content must include this text
     * @return upgrade object
     */
    ContentUpgrade doCheckPageRendering(String textPresent);

    /**
     * Checks if the containing page renders with status code 200 and (not) contains given text.
     * 
     * @param textPresent    page content must include this text (can be null)
     * @param textNotPresent page content must not include this text (can be null)
     * @return upgrade object
     */
    ContentUpgrade doCheckPageRendering(String textPresent, String textNotPresent);

    /**
     * Print path
     * 
     * @return upgrade object
     */
    ContentUpgrade printPath();

    /**
     * Print property
     *
     * @param property property name
     * @return upgrade object
     */
    ContentUpgrade printProperty(String property);

    /**
     * Prints the properties json
     *
     * @return upgrade object
     */
    ContentUpgrade printJson();

    /**
     * Saves all changes to repository.
     * 
     * @throws PersistenceException error during execution
     * @throws AecuException        other error
     */
    void run() throws PersistenceException, AecuException;

    /**
     * Performs a dry-run. No changes are written to CRX.
     * 
     * @throws PersistenceException error doing dry-run
     * @throws AecuException        other error
     */
    void dryRun() throws PersistenceException, AecuException;

    /**
     * Executes a run or a dryRun depending on the dryRun parameter value.
     *
     * @param dryRun dryRun option
     * @throws PersistenceException error during execution
     * @throws AecuException        other error
     */
    void run(boolean dryRun) throws PersistenceException, AecuException;

}

